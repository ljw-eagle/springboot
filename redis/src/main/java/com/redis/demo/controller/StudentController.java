package com.redis.demo.controller;

import com.redis.demo.bean.Student;
import com.redis.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MVC中的c
 */
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/saveStudet")
    private void saveStudent(Student student){
        studentService.saveStudent(student);
    }
}
