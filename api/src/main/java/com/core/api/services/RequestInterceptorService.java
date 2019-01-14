package com.core.api.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

@Service
public class RequestInterceptorService implements HandlerInterceptor {

    @Autowired
    public JWTService jwtService;

    @Autowired
    JWTData jwtData;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        
        if (auth == null || auth.length() == 0) {
            response.setStatus(401);
            response.getOutputStream().write("Unauthenticated: missing Authorization header".getBytes());
            return false;
        } else {

            // check the token
            try {
                String rawToken = auth.substring("Bearer ".length());
                Jws<Claims> claims = jwtService.getClaims(rawToken);
                jwtData.setClaims(claims);
                return true;
            } catch (JwtException e) {
                response.setStatus(403);
                response.getOutputStream().write("Unauthorized: Bad Authorization token".getBytes());    
                return false;
            }
        }
    }
}