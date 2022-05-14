package com.atguigu.eduservice.controller;

import com.atguigu.commomutils.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class EduUserController {

    @PostMapping("/login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R getInfo(){
        return R.ok().data("roles", "[admin]").
                data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").
                data("name","admin");
    }

}
