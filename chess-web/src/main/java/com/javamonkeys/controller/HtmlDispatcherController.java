package com.javamonkeys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HtmlDispatcherController{

    public HtmlDispatcherController(){};

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @RequestMapping("/registration")
    public String registration(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "registration";
    }

    @RequestMapping("/userprofile")
    public String userProfile(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "userprofile";
    }

}
