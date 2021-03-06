package com.atguigu.eduservice.client;

import com.atguigu.commomutils.R;

import java.util.List;

public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R deleteAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
