package com.core.api.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
public class RequestInterceptorService implements HandlerInterceptor {

    @Autowired
    public JWTService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        
        if (auth == null || auth.isEmpty()) {
            response.setStatus(401);
            response.getOutputStream().write("Unauthenticated: missing Authorization header".getBytes());
            return false;
        } else {
            String rawToken = auth.substring("Bearer ".length());
            boolean res = jwtService.validateAndInitToken(rawToken);
                
            if (res == false) {
                response.setStatus(403);
                response.getOutputStream().write("Unauthorized: Bad Authorization token".getBytes());    
            }
            return res;
        }
    }
}