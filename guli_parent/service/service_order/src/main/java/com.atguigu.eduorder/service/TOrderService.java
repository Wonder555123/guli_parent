package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-05-07
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId,String memberIdByJwtToken);

}
