package com.atguigu.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.commomutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;
//    添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo ){

        eduVideoService.save(eduVideo);
        return R.ok();
    }

//    删除小节
//    TODO
    @DeleteMapping("/{videoId}")
    public R deleteVideo(@PathVariable String videoId){

        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        eduVideoService.removeById(videoId);
//        由小节id删除视频id
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteAlyVideo(videoSourceId);
        }
        return R.ok();
    }

//    修改小节
    @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo ){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }


}

