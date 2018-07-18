package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


/**
 * 1、接口式编程
 * 原生：		Dao		====>  DaoImpl
 * mybatis：	Mapper	====>  xxMapper.xml
 * <p>
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * （将接口和xml进行绑定）
 * EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * sql映射文件：保存了每一个sql语句的映射信息：
 * 将sql抽取出来。
 */
public class MyBatisTest {

    //获得SqlSessionFactory对象
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * 1、根据xml配置文件（全局配置文件），创建一个SqlSessionFactory对象
     * 有数据源一下运行环境信息
     * 2、sql映射文件，配置了每一个sql，以及sql的封装规则等
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码
     * 1）、根据全局配置文件得到SqlSessionFactory；
     * 2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        //2、获取SqlSession实例，能直接执行已经映射的sql语句
        SqlSession session = sqlSessionFactory.openSession();
        try {
            //参数：sql语句唯一标识，执行sql要用的参数
            Employee employee = session.selectOne("com.atguigu.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);

        } finally {
            session.close();
        }
    }


    @Test
    public void test02() throws IOException {

        //1、获得sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取SqlSession对象
        SqlSession session = sqlSessionFactory.openSession();
        try {
            //3、获得接口实现类对象
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            session.close();
        }
    }


    /**
     * 测试增删改
     * 1、mybatis允许增删改直接定义以下类型返回值
     * Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * sqlSessionFactory.openSession();===》手动提交
     * sqlSessionFactory.openSession(true);===》自动提交
     *
     * @throws IOException
     */
    @Test
    public void test03() throws IOException {
        //获得sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //1、获取到sqlSession不会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            //测试添加
            Employee employee = new Employee(null, "jerry", "jerry@atguigu.com", "1");
            mapper.addEmp(employee);
            System.out.println(employee.getId());

            //测试修改
            //Employee employee = new Employee(1, "jerry", "jerry@atguigu.com", "0");
            //mapper.updateEmp(employee);

            //测试删除
            //mapper.deleteEmpById(2);

            //2、手动提交
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }


    //根据多个查询条件参数查询员工信息
    @Test
    public void test05() throws IOException {
        //获得sqlSessionfactory工厂
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获得sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //获取接口的实现类对象,会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            //查询
            Employee tom = mapper.getEmpByIdAndLastName(1, "jerry");
            System.out.println(tom);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sqlSession.close();
        }

    }

    //根据多个查询条件参数查询员工信息，封装成map
    @Test
    public void test06() throws IOException {
        //获得sqlSessionfactory工厂
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获得sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //获取接口的实现类对象,会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            //查询
            HashMap<String, Object> map = new HashMap<>();
            map.put("id","1");
            map.put("lastName","jerry");
            Employee employee = mapper.getEmpByMap(map);
            System.out.println(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }


}
