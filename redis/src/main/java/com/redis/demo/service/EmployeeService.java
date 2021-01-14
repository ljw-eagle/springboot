package com.redis.demo.service;

import com.redis.demo.bean.Employee;
import com.redis.demo.repository.EmployeeRepository;
import com.redis.demo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 前缀
     */
    public static final String KEY_PREFIX_VALUE = "redis:employee:value:";

    public String getEmployee(String id){
        String ename="";
        if(redisUtil.hasKey(KEY_PREFIX_VALUE+id)){
             ename = (String) redisUtil.get(KEY_PREFIX_VALUE+id);
        }else{
            List<Employee> employee = employeeRepository.findAll();
            //redisUtil.set(KEY_PREFIX_VALUE+id,employee.get(0).getEname());
            //设置时间
            redisUtil.incr(KEY_PREFIX_VALUE+id,9);
            redisUtil.set(KEY_PREFIX_VALUE+id,employee.get(0).getEname(),10);
            ename = employee.get(0).getEname();
        }
        return  ename;
    }

    public Employee getEmployeeObject(int id){
        Employee employee = employeeRepository.findByEid(id);
        return  employee;
    }

    public List<Employee> getEmployeeList(String id){
        List<Employee> employee = employeeRepository.findAll();
        return  employee;
    }

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }


}
