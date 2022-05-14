package com.atguigu.educenter.controller;


import com.atguigu.commomutils.JwtUtils;
import com.atguigu.commomutils.R;
import com.atguigu.commomutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember ucenterMember){

        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);

    }

    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo){

        ucenterMemberService.register(registerVo);
        return R.ok();

    }

//    根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        return R.ok().data("ucenterMember",ucenterMember);

    }

//    根据用户id获取用户信息
    @GetMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable String id){

        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;

    }

//    查询某一天的注册人数
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){

        Integer count = ucenterMemberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);

    }



}

