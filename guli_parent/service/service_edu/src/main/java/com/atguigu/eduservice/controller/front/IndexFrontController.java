package com.atguigu.eduservice.controller.front;

import com.atguigu.commomutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;
//    1-查询前八条的热门课程
//    2-查询前四的名师
    @GetMapping("/index")
    public R index(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        QueryWrapper<EduTeacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("id");
        wrapper2.last("limit 4");
        List<EduCourse> eduCourseList = eduCourseService.list(wrapper);
        List<EduTeacher> eduTeacherList = eduTeacherService.list(wrapper2);
        return R.ok().data("eduList",eduCourseList).data("teacherList",eduTeacherList);
    }

}
