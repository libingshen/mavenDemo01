package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    //根据员工姓名查询，结果封装到list集合
    public List<Employee> getEmpByLastName(String lastName);

    //根据多个查询条件参数查询员工信息，封装成map
    public  Employee getEmpByMap(Map<String,Object> map);
    //根据多个查询条件参数查询员工信息
    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);


//    @Select("select * from tbl_employee where id = #{id}")
    public Employee getEmpById(Integer id);

    public Long addEmp(Employee employee);

    public boolean updateEmp(Employee employee);

    public void deleteEmpById(Integer id);

}
