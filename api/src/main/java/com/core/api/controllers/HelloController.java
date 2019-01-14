package com.core.api.controllers;

import com.core.api.services.JWTData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private JWTData jwtData;

    public void setJwtData(JWTData jwtData) {
        this.jwtData = jwtData;
    }

    @RequestMapping("/hi")
    @ResponseBody
    public String hello() {
        String username = jwtData.get("email");
        if (username != null && username.length() != 0) {
            return "Hi " + username  + " from core API";
        } else {
            return "Hi GUEST from core API";
        }
    }
}