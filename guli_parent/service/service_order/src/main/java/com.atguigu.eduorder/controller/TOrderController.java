package com.atguigu.eduorder.controller;

import com.atguigu.commomutils.JwtUtils;
import com.atguigu.commomutils.R;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 * @author testjava
 * @since 2022-05-07
 */

@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService tOrderService;
// 下单
    @PostMapping("/createOrder/{courseId}")
    public R SaveOrder(@PathVariable String courseId, HttpServletRequest request){

        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = tOrderService.createOrders(courseId,memberIdByJwtToken);
        return R.ok().data("orderId",orderNo);

    }

//    根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = tOrderService.getOne(wrapper);
        return R.ok().data("item",order);

    }


}

