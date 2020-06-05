package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程简介 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/eduservice/description")
public class EduCourseDescriptionController {
    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.saveCourseInfo(courseInfoVo);
        return R.ok();
    }
}

