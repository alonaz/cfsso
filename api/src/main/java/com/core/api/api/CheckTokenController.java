package com.core.api.api;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckTokenController {

    @RequestMapping("/token")
    @ResponseBody
    public String token(@RequestHeader(value="Authorization", defaultValue="none") String auth) {
        return auth;
    }
}