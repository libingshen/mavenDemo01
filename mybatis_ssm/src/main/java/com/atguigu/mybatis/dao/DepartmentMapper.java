package com.atguigu.mybatis.dao;


import com.atguigu.mybatis.bean.Department;

public interface DepartmentMapper {

    public Department getDeptById(Integer id);

    //根据部门id查出部门信息，同时查出该部门的员工信息
    public Department getDeptByIdPlus(Integer id);

    //根据部门id查出部门信息，同时查出该部门的员工信息，使用分步查询
    public Department getDeptByIdStep(Integer id);
}
