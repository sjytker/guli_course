package com.example.demo.edu.controller;


import com.example.demo.edu.entity.Teacher;
import com.example.demo.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
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

