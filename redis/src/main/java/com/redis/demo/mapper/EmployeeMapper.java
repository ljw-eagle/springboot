package com.redis.demo.mapper;

import com.redis.demo.bean.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface  EmployeeMapper {
    Employee findEmployeeById(String eid);
    void saveEmploye(Employee employee);
}
