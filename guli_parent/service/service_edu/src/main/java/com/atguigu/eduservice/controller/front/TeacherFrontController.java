package com.atguigu.eduservice.controller.front;

import com.atguigu.commomutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    //分页查询讲师的方法
    @GetMapping("/getTeacherfrontList/{current}/{limit}")
    public R getTeacherfrontList(@PathVariable long current, @PathVariable long limit){

        Page<EduTeacher> page = new Page<>(current, limit);
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(page);
        return R.ok().data(map);

    }

    @GetMapping("/getTeacherfrontList/{teacherId}")
    public R getTeacherfrontInfo(@PathVariable String teacherId){
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> eduCourseList = eduCourseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",eduCourseList);
    }


}
