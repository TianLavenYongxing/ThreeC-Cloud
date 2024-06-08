package com.threec.auth.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    public String testGet() {
        return "GET 方法测试";
    }

    @PostMapping()
    public String testPost() {
        return "POST 方法测试";
    }

    @PutMapping
    public String testPut() {
        return "PUT 方法测试";
    }

    @DeleteMapping
    public String testDelete() {
        return "DELETE 方法测试";
    }

    @PatchMapping
    public String testPatch() {
        return "PATCH 方法测试";
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public String testOptions() {
        return "OPTIONS 方法测试";
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public String testHead() {
        return "HEAD 方法测试";
    }

}
