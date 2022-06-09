package com.bookshop.system.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liditao
 * @since 2022-06-09
 */
@RestController
@RequestMapping("/test")
public class UsersController {

    @GetMapping("/hello")
    public String hello(){
        return "test";
    }

}
