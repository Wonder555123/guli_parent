package com.atguigu.eduorder.controller;


import com.atguigu.commomutils.R;
import com.atguigu.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService tPayLogService;
//    生成微信支付二维码的接口
//    参数是订单数
    @GetMapping("/create/{orderNo}")
    public R createNative(@PathVariable String orderNo){

//        返回信息 包括二维码的地址，还有其他信息
        Map map = tPayLogService.createNative(orderNo);
        return R.ok().data(map);

    }

//    查询支付的状态
    @GetMapping("/queryStatus/{orderNo}")
    public R queryStatus(@PathVariable String orderNo){

        Map<String,String> map = tPayLogService.queryPayStatus(orderNo);
        if(map==null){
            return R.error().message("支付出错了");
        }
//        如果返回map里面不为空,通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")){
//            添加记录到支付表,更新订单状态
            tPayLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功!");
        }
        return R.ok().message("支付中!");

    }

}

