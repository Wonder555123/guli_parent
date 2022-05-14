package com.atguigu.eduservice.controller;

import com.atguigu.commomutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-12
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;
//    1、查询讲师表所有数据
//    rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        try {
//            int a = 10/0;
        }catch(Exception e) {
            throw new GuliException(20001,"出现自定义异常");
        }
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

//    删除
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID") @PathVariable String id){

        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

//    分页查询讲师的方法
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){

        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);

    }

//    条件查询带分页方法
//    这里注意使用RequestBody必须要使用Post进行请求，使用Get会出错
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){

//        创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
//        构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//        wrapper(多条件组合查询)
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }else if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }else if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }else if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
//        调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);

    }

//    添加讲师接口的方法
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/{id}")
    public R getById(@ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("/{id}")
    public R updateById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id,
                        @ApiParam(name = "teacher", value = "讲师对象", required = true)
                        @RequestBody EduTeacher eduTeacher){
        eduTeacher.setId(id);
        teacherService.updateById(eduTeacher);
        return R.ok();
    }

}

