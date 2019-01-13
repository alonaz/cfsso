package com.core.api.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hi")
    @ResponseBody
    public String hello(HttpServletResponse response) {
        String username = response.getHeader("username");
        if (username != null && username.length() != 0) {
            return "Hi " + username  + " from core API";
        } else {
            return "Hi GUEST from core API";
        }
    }
    
}