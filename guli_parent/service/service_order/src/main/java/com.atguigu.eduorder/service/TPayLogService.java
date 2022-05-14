package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-05-07
 */
public interface TPayLogService extends IService<TPayLog> {

//    生成微信支付二维码接口
    Map createNative(String id);
    Map<String,String> queryPayStatus(String orderNo);
    void updateOrdersStatus(Map<String,String> map);

}
