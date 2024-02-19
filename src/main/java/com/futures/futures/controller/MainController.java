package com.futures.futures.controller;

import org.springframework.web.bind.annotation.RestController;

import com.futures.futures.service.FutureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class MainController {

    @Autowired
    private FutureService service;

    @GetMapping("service")
    public String getMethodName() throws Exception {
        service.startThreads();
        return "Success";
    }
    

}