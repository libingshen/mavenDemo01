package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapperDynamicSQL {

    //根据动态查询条件查询出员工信息
    //携带了哪个字段查询条件就带上这个字段的值
    public List<Employee> getEmpsByConditionIf(Employee employee);

    //动态查询条件，测试trim
    public List<Employee> getEmpsByConditionTrim(Employee employee);

    //动态查询条件，测试choose
    public List<Employee> getEmpsByConditionChoose(Employee employee);

    //动态修改条件，更新员工
    public void updateEmp(Employee employee);

    //根据封装好的多个员工id查询，结果集封装在给定集合中的
    public List<Employee> getEmpsByConditionForeach(@Param("ids")List<Integer> ids);

    //批量保存员工
    public void addEmps(@Param("emps") List<Employee> employees);



}
