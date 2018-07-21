package com.atguigu.mybatis.bean;

import java.util.List;

public class Department {

    private Integer id;
    private String deptmentName;
    private List<Employee> emps;

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptmentName() {
        return deptmentName;
    }

    public void setDeptmentName(String deptmentName) {
        this.deptmentName = deptmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", deptmentName='" + deptmentName + '\'' +
                ", emps=" + emps +
                '}';
    }
}
