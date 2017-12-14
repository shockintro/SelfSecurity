package com.lihf.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 假设每个返回的字符串信息是用户请求的资源
 * @author lihf
 * @date 2017-12-14
 */
@RestController
public class HttpController {
    @PreAuthorize("hasRole('RESOURCE')")
    @GetMapping("/")
    public String hello(){
        return "HELLO WORLD!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/static/hello")
    public String staticHello(){
        return "static_hello!".toUpperCase();
    }

    @PreAuthorize("hasRole('RESOURCE')")
    @GetMapping("/resource/hello")
    public String resourceHello(){
        return "resource_hello!".toUpperCase();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/hello")
    public String userHello(){
        return "user_hello!".toUpperCase();
    }
}
