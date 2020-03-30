/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.fw.test;

import com.kdg.fs24.spring.boot.api.AbstractSpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 *
 * @author N76VB
 */
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
@ComponentScan
public class TestSpringBootApp extends AbstractSpringBootApplication {

    public static void main(final String[] args) {
        //SpringApplication.run(SpringBootApplication.class, args);
        //SpringApplication.run(TestSpringBootApp.class, args);
        //SpringApplication.run(TestSpringBootApp.class, args);
        TestSpringBootApp.runSpringBootApplication(args, TestSpringBootApp.class);

    }

//    @GetMapping("/hello")
//    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return String.format("<b><s>Hello %s!!!!!</s></b>", name);
//    }
}
