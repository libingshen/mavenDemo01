package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface EmployeeMapper {


    //根据多个查询条件参数查询员工信息，封装成map
    public  Employee getEmpByMap(Map<String,Object> map);
    //根据多个查询条件参数查询员工信息
    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    public Employee getEmpById(Integer id);

    public Long addEmp(Employee employee);

    public boolean updateEmp(Employee employee);

    public void deleteEmpById(Integer id);

}
