package com.core.api.controllers;

import com.core.api.configuration.VcapServicesUaaPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckTokenController {

    @Autowired
    public VcapServicesUaaPublicKey vsupk;

    @RequestMapping("/token")
    @ResponseBody
    public String token(@RequestHeader(value="Authorization", defaultValue="none") String auth) {
        return auth;
    }

    @RequestMapping("/pkey")
    @ResponseBody
    public String pkey() {
        String key = vsupk.getVerificationKey();
        if ((key == null) || key.isEmpty()) {
            return "Cannot find public verification key";
        } else {
            return key;
        }
    }
    
    @RequestMapping("/rawpkey")
    @ResponseBody
    public String rawpkey() {
        String key = vsupk.getRawVerificationKey();
        if ((key == null) || key.isEmpty()) {
            return "Cannot find public verification key";
        } else {
            return key;
        }
    }    
}