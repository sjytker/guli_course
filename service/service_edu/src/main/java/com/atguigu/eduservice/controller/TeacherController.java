package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.service.TeacherService;
import com.atguigu.eduservice.entity.Teacher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-19
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("findAll")
    public List<Teacher> findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return list;
    }

    @DeleteMapping("{id}")
    public boolean removeById(@PathVariable String id) {
        return teacherService.removeById(id);
    }
}

