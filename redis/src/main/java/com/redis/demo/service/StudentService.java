package com.redis.demo.service;

import com.redis.demo.bean.Student;
import com.redis.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑层
 */
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    /**
     * 保存学生
     * @param student
     */
    public void saveStudent(Student student){
        studentRepository.save(student);
    }

}
