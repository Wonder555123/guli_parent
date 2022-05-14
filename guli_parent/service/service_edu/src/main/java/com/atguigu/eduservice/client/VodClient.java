package com.atguigu.eduservice.client;

import com.atguigu.commomutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R deleteAlyVideo(@PathVariable String id);

    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
