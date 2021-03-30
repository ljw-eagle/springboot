package com.redis.demo.controller;

import com.redis.demo.bean.Student;
import com.redis.demo.mapper.StudentMapper;
import com.redis.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MVC中的c
 */
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping("/saveStudet")
    private void saveStudent(Student student){
        studentService.saveStudent(student);
    }

    @RequestMapping("/getAllStudentList")
    @ResponseBody
    public List<Student> getAllStudentList(){
        return studentMapper.getAllStudentList();
    }
}
