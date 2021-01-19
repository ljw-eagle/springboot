package com.redis.demo.mapper;

import com.redis.demo.bean.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    /**
     * 注解的方式
     * @return
     */
    @Select("select * from student")
    List<Student> getAllStudentList();

    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age"),
            @Result(property = "sex", column = "sex")
    })
    @Select("select name as u,age as a,sex as id from Student where id=#{id}")
    Student getStudentById(Long id);

    @Select("select * from Student where name like concat('%',#{name},'%')")
    List<Student> getStudentsByName(String name);

    @Insert({"insert into Student(name,age,sex) values(#{name},#{age},#{sex)"})
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    Integer addStudent(Student Student);

    @Update("update Student set name=#{name},age=#{age} where id=#{id}")
    Integer updateStudentById(Student Student);

    @Delete("delete from Student where id=#{id}")
    Integer deleteStudentById(Integer id);
}
