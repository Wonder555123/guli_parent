package com.atguigu.staservice.controller;

import com.atguigu.commomutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-05-09
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
   private StatisticsDailyService staService;

//   统计某一天的注册人数，生成统计数据
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day) {

        staService.registerCount(day);
        return R.ok();

    }

}

