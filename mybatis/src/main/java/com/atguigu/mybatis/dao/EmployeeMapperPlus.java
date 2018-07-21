package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;

import java.util.List;

public interface EmployeeMapperPlus {


    public Employee getEmpById(Integer id);

    //查询员工的同时查询部门信息
    public Employee getEmpAndDept(Integer id);

    //查询员工的同时查询部门信息，使用分步查询
    public Employee getEmpByIdStep(Integer id);

    //根据查询部门信息带出的id查询员工信息，封装到List集合
    public List<Employee> getEmpByDeptId(Integer id);
}
