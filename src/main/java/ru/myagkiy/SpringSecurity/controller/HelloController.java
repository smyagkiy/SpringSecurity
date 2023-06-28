package ru.myagkiy.SpringSecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.myagkiy.SpringSecurity.services.AdminService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HelloController {
    private final AdminService adminService;
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
    @GetMapping(value = "/admin")
    public String adminPage(){
        adminService.doAdminStuff();
        return "admin";
    }
}
