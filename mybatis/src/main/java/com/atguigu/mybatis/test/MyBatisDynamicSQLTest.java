package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisDynamicSQLTest {

    //获得SqlSessionFactory对象
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    //根据动态查询条件查询员工信息
    @Test
    public void test01() throws IOException {
        //获得sqlSessionfactory工厂
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获得sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //获取接口的实现类对象,会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            //查询
            Employee employee = new Employee(null, "%r%", "jerry@atguigu.com", "0");
            List<Employee> employees = mapper.getEmpsByConditionIf(employee);
            for (Employee emp : employees) {
                System.out.println(emp);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    //根据动态查询条件查询员工信息,使用trim
    @Test
    public void test02() throws IOException {
        //获得sqlSessionfactory工厂
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获得sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //获取接口的实现类对象,会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            //查询
            Employee employee = new Employee(null, "%r%", null, null);
            List<Employee> employees = mapper.getEmpsByConditionTrim(employee);
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
