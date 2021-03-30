package com.redis.demo.mapper;

import com.redis.demo.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  EmployeeMapper {
    Employee findEmployeeById(String eid);
    void saveEmploye(Employee employee);
    void batchInsertEmploye(List<Employee> list);
}
