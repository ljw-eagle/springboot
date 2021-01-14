package com.redis.demo.repository;

import com.redis.demo.bean.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 数据层
 */
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}
