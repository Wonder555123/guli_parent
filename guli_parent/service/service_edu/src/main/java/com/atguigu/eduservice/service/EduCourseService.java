package com.atguigu.eduservice.service;


import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.frontVo.CourseFrontVo;
import com.atguigu.eduservice.frontVo.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
    CourseInfoVo getCourseInfo(String courseId);
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    CoursePublishVo publicCourseInfo(String courseId);
    void removeCourse(String courseId);
    Map<String,Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo);
    CourseWebVo getBaseCourseInfo(String courseId);

}
