package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    //多条记录封装一个map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
    //@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("id")
    public Map<Integer,Employee> getEmpByLastNameReturnMap(String lastName);

    //根据员工id查询，结果集封装到map
    //返回一条记录的map；key就是列名，值就是对应的值
    public Map<String,Object> getEmpByIdReturnMap(Integer id);

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
