package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.mapper.TPayLogMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-05-07
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService tOrderService;

    @Override
    public Map createNative(String orderNo) {
        try{
//        1-根据订单号查询订单信息
            QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            TOrder order = tOrderService.getOne(wrapper);
//        2-使用map设置生成二维码需要的参数
            Map m = new HashMap();
//            微信id
            m.put("appid", "wx74862e0dfcf69954");
//            商户号
            m.put("mch_id", "1558950191");
//            生成随机字符串，保证二维码的不同
            m.put("nonce_str", WXPayUtil.generateNonceStr());
//            二维码名称
            m.put("body", order.getCourseTitle());
//            唯一标识，订单号
            m.put("out_trade_no", orderNo);
//            订单的价格
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
//            支付的ip地址
            m.put("spbill_create_ip", "127.0.0.1");
//            回调地址
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
//            支付的类型 native生成二维码支付类型
            m.put("trade_type", "NATIVE");
//        3-发送httpclient请求，传递参数xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            商户key
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
//        4-得到发送请求返回结果,结果是xml结构
            String content = client.getContent();
//            把xml格式转换为map集合,把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
//            最终返回数据的封装
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
//            返回二维码操作状态码
            map.put("result_code", resultMap.get("result_code"));
//            二维码的地址
            map.put("code_url", resultMap.get("code_url"));
            return  map;
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"生成二维码失败!");
        }
    }

//    查询订单支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try{
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

//    添加支付记录和更新订单状态
    @Override
    public void updateOrdersStatus(Map<String,String> map) {

        //获取订单id
        String orderNo = map.get("out_trade_no");

        //根据订单id查询订单信息
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = tOrderService.getOne(wrapper);
        if(order.getStatus().intValue() == 1) return;
        order.setStatus(1);
        tOrderService.updateById(order);

        //记录支付日志
        TPayLog payLog=new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);//插入到支付日志表

    }


}
