# SAP Cloud Platform Cloud Foundry UAA Authentication super simple example

## Motivation
I could not find a minimal example on how to authenticate users on SAP Cloud Platform Cloud Foundry. There are many blogs and documentation, but none I could find explain the authentication in it's mininal form.

I wanted to create a dead simple example without any usage of security frameworks / libs so that it is easy to understand how this sort of authentication works.

## Possible future improvements to this example.

- Currently this example supports only default platform identity provider. But wiring this into your own identiry provider should be simple.
- Currently anyone with an account on the default platform identity provider can access the backend.

## Additional simplificatins for future:

- Get rid of the app router component, and instead directly authenticate to UAA from the java backend application.
- Allow to configure which user groups can access the application.
- Add an option for pubilc resources on the backend.

## How Authentication in SAP Cloud Platform Cloud Foundry works
Cloud Foundry has a component called the UAA, this component implements oauth2 spec, and allows to authenticate users against different backends such as LDAP, SAML2 Identity Providers etc.
An application which runs on CF can delegate to the UAA the authentication of end users. The UAA will generate a signed JWT token and forward it to the application. 

In this example there are 2 components:
- The app-router - which in this case acts as the wiring between our backend application and the UAA. The app router will redirect the users browser to the UAA login screen, and then handle the communication with the UAA needed to get the generated JWT. Then the app router will forward the token to any other application which was configured in a standard Authorization header.
- The api application is a spring boot application which reads the Authorization header, checks the signiture correctness against the UAA public key, and allows for any class in the application to get information from the token, in case the token is valid. This flow is done manually without usage of any security frameworks such as spring security.

#How to run it

1. git clone this repo
2. Login to CF with your org / space via the cf cli
3. Create uaa service:
```bash
cf create-service xsuaa application xsuaa -c '{"xsappname":"cfsso","tenant-mode":"dedicated"}'
```
4. Package the spring boot application
```bash
cd api
mvn package
cd ..
```
4. You might need to change the manifest.yml file and point it to the corect url where your api application would be deployed. The URL depends on your CF provider, your account, and some additional params.
5. Deploy both applications to Cloud Foundry:
```bash
cf push
```
6. Check the /private url of your application by opening the browser and pointing it to the correct URL of the app-router. for example:
```bash
https://cfsso-app-router.cfapps.eu10.hana.ondemand.com/core-api/private
```
This is the url when I use SAP Cloud Platform trail account.
7. You will be redirected to a login screen. Login with the same account you used when you registered to SAP Cloud Platform trail account.
8. Once you login you will be redirected to /private controller and will see the expected message.

# Troubleshooting
The api spring boot application also uses the spring boot actuator. You can see special configuration of the actuator in the application.properties file. This allows us easily see the environment variables exposed to the application by using the /actuator/env endpoint for example

```bash
https://cfsso-app-router.cfapps.eu10.hana.ondemand.com/core-api/actuator/env 
```

And also will allow you to see the actual http Authorization header that the app-router application is adding to the HTTP request by useing the/actuator/httptrace endpoint for example:

```bash
https://cfsso-app-router.cfapps.eu10.hana.ondemand.com/core-api/actuator/httptrace 
```