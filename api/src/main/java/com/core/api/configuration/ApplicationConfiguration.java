package com.core.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("vcap.services.xsuaa.credentials")
public class ApplicationConfiguration {

    private String verificationkey;

    public void setVerificationkey(String key) {
        this.verificationkey = key;
    }
    
    public String getRawVerificationKey() {
        String dirty = this.verificationkey;
        dirty = dirty.replace("-----BEGIN PUBLIC KEY-----", "");
        dirty = dirty.replace("-----END PUBLIC KEY-----", "");            
        return dirty;
    }    
}