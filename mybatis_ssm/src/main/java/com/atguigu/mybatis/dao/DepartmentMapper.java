package com.atguigu.mybatis.dao;


import com.atguigu.mybatis.bean.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentMapper {

    public Department getDeptById(Integer id);

}
