package com.atguigu.eduorder.client;

import com.atguigu.commomutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-edu")
public interface UcenterClient {

    @GetMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable("id") String id);

}
