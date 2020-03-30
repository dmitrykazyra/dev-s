/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.spring.fw.test;

import com.kdg.fs24.spring.boot.api.AbstractSpringBootController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author N76VB
 */
//@Controller
@RestController
public class TestPringBootController extends AbstractSpringBootController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("<b><s>Helloooooooooooooo %s!!!!!</s></b>", name);
    }

    @GetMapping("/error404")
    public String error(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("<b><s>errror! %s!!!!!</s></b>", name);
    }
}
