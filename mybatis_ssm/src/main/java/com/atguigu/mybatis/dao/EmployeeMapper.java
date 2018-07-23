package com.atguigu.mybatis.dao;


import com.atguigu.mybatis.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {


    //    @Select("select * from tbl_employee where id = #{id}")
    public Employee getEmpById(Integer id);

    public List<Employee> getEmps();


}
