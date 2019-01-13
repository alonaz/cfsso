package com.core.api.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

@Component
public class MyCustomInterceptor implements HandlerInterceptor {

    @Autowired
    public JWTService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        
        if (auth == null || auth.length() == 0) {
            response.sendError(401, "Unauthenticated: missing Authorization header");
            return false;
        } else {

            // check the token

            try {
                String rawToken = auth.substring("Bearer ".length());
                Jws<Claims> claims = jwtService.getClaims(rawToken);
                String userName = claims.getBody().get("given_name").toString();
                //OK, we can trust this JWT
                response.addHeader("username", userName);
                return true;
            } catch (JwtException e) {
                response.sendError(403, "Unauthorized: Bad token");
                return false;
            }
        }
    }
}