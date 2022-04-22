package com.lina.springmagazine.controller;

import com.lina.springmagazine.security.UserImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserImpl userImpl) {
        System.out.println("in home");
        model.addAttribute("username", userImpl.getUsername());

        return "index";
    }

    @GetMapping("/write")
    public String write() {
        return "write";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String loginError() {
        return "error";
    }

    @GetMapping("/post/{id}")
    public String viewPost(Model model, @PathVariable Long id) {
        String userHome = System.getProperty("user.home");
        model.addAttribute("postNo", id);
        model.addAttribute("path", userHome+"/images/");
        return "board";
    }

}
