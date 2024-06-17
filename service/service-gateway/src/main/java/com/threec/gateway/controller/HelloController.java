package com.threec.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.threec.tools.utils.R;

/**
 * Class HelloController.
 * <p>
 *
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 14/6/24
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    @GetMapping()
    public R<Object> hello(){
        return new R<>().ok("Hello");
    }
}