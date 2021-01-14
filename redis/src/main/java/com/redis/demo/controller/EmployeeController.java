package com.redis.demo.controller;

import com.redis.demo.bean.Employee;
import com.redis.demo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class EmployeeController {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    /**
     * http://localhost:8080/getName/1
     * @PathVariable 是从一个URI模板里面来填充
     * @param id
     * @return
     */
    @RequestMapping("/getName/{id}")
    @ResponseBody
    public String getName(HttpServletRequest request, @PathVariable("id") String id){
        String ename = employeeService.getEmployee(id);
        return ename;
    }

    /**
     * http://localhost:8080/getName1?id=1
     * @RequestParam 是从 request 里面拿取值
     * @param id
     * @return
     */
    @RequestMapping("/getName1")
    public String getName1(HttpServletRequest request, @RequestParam("id")String id){
        String ename = employeeService.getEmployee(id);
        return ename;
    }

    @PostMapping("/getName2")
    public String getName2(String id){
        String ename = employeeService.getEmployee(id);
        return ename;
    }

    @GetMapping("/getName3/{id}")
    public String getName3(String id){
        String ename = employeeService.getEmployee(id);
        return ename;
    }

    @RequestMapping("/getEmployee")
    public Employee getEmployee(int eid,String ename,Employee employee){
        Employee employee1 = employeeService.getEmployeeObject(eid);
        return employee1;
    }
    @RequestMapping("/getEmployeeList")
    public List<Employee> getEmloyeeList(String id){
        List<Employee> employee = employeeService.getEmployeeList(id);
        return  employee;
    }

    @RequestMapping("/saveEmployee")
    public void saveEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
    }


}
