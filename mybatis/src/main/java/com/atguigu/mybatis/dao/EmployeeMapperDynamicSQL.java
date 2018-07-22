package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;

import java.util.List;

public interface EmployeeMapperDynamicSQL {

    //根据动态查询条件查询出员工信息
    //携带了哪个字段查询条件就带上这个字段的值
    public List<Employee> getEmpsByConditionIf(Employee employee);

    //动态查询条件，测试trim
    public List<Employee> getEmpsByConditionTrim(Employee employee);

}
