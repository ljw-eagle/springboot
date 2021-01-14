package com.redis.demo.repository;

import com.redis.demo.bean.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEid(int id);
}
