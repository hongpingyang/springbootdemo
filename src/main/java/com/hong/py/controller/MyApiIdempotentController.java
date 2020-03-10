package com.hong.py.controller;

import com.hong.py.annotation.ApiIdempotent;
import com.hong.py.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/token")
public class MyApiIdempotentController {


    @Autowired
    private TokenService tokenService;

    @RequestMapping("/test")
    @ResponseBody
    @ApiIdempotent
    public String doAction() {
        System.out.println("test");
        return "test";
    }

    @GetMapping
    public void  token(HttpServletRequest request) {
        String token = tokenService.createToken();
        System.out.println("获取到的token:"+token);
    }

}
