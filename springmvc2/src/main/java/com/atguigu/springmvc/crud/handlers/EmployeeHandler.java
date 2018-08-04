package com.atguigu.springmvc.crud.handlers;

import com.atguigu.springmvc.crud.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author slb
 * @create 2018-08-04 10:36
 */

@Controller
public class EmployeeHandler {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping("/emps")
    public String list(Map<String,Object> map){
        System.out.println("/emps=============>");
        map.put("employees",employeeDao.getAll());
        return "list";
    }
}
