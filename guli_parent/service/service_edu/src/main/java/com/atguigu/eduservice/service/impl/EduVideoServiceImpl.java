package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
//    TOOD 删除小节的同时，还要删除对应的视频
    @Override
    public void removeVideoCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",courseId);
        wrapper2.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper2);
        List<String> list = new ArrayList<>();
        for (int i=0;i<eduVideos.size();i++){
            String videoSourceId = eduVideos.get(i).getVideoSourceId();
            if(StringUtils.isEmpty(videoSourceId)){
                list.add(videoSourceId);
            }
        }
        if(list!=null&&list.size()>0){
            vodClient.deleteBatch(list);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
