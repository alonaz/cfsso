package com.core.api.controllers;

import com.core.api.services.JWTData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @Autowired
    private JWTData jwtData;

    @RequestMapping("/public")
    @ResponseBody
    public String publicHello() {
        String username = jwtData.get("email");
        return "Hi " + username + ". This is a public controller";
    }
    
}