package com.threec.auth.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "我们的测试";
    }
}
