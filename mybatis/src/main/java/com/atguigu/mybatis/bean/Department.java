package com.atguigu.mybatis.bean;

public class Department {

    private Integer id;
    private String deptmentName;

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
                '}';
    }
}
