package com.atguigu.springmvc.crud.handlers;

import com.atguigu.springmvc.crud.dao.DepartmentDao;
import com.atguigu.springmvc.crud.dao.EmployeeDao;
import com.atguigu.springmvc.crud.entities.Employee;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author slb
 * @create 2018-08-04 10:36
 */

@Controller
public class EmployeeHandler {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    //删除一个员工
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
    //保存员工
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    public String save(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //表单回显
    @RequestMapping(value = "emp", method = RequestMethod.GET)
    public String input(Map<String, Object> map) {
        map.put("departments", departmentDao.getDepartments());
        map.put("employee", new Employee());
        return "input";
    }

    @RequestMapping("/emps")
    public String list(Map<String, Object> map) {
        System.out.println("/emps=============>");
        map.put("employees", employeeDao.getAll());
        return "list";
    }

    @RequestMapping("/json")
    public String list2Json(Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> rowMap = new HashMap();
        Map<String, Object> rowMap2 = new HashMap();
        Map<String, List> resultMap = new HashMap();

        rowMap.put("资产编码", "000000536535");
        rowMap.put("索引", "AN5516");
        rowMap.put("资产目录", "0901010102");
        rowMap.put("项目编码", null);
        rowMap.put("项目名称", null);
        rowMap.put("txa50结构规格程式", "HU1A");
        rowMap.put("txt50资产描述", "千兆以太网接口板");
        rowMap.put("资本化日期", "20111231");
        rowMap.put("地址", "定安龙门2楼综合机房");
        rowMap.put("节点名称", "定安龙门");

        rowMap2.put("资产编码", "000000536140");
        rowMap2.put("索引", "AN5516");
        rowMap2.put("资产目录", "0901010102");
        rowMap2.put("项目编码", null);
        rowMap2.put("项目名称", null);
        rowMap2.put("txa50结构规格程式", "AN5516机框");
        rowMap2.put("txt50资产描述", "烽火OLT机框");
        rowMap2.put("资本化日期", "20111231");
        rowMap2.put("地址", "定安龙门2楼综合机房");
        rowMap2.put("节点名称", "定安龙门");

        list.add(rowMap);
        list.add(rowMap2);

        JSONArray jsonArray = List2Json(list);

        map.put("jsonArray", jsonArray);
        return "list";
    }

    public static JSONArray List2Json(List<Map<String, Object>> list) {
        JSONArray json = JSONArray.fromObject(list);
        return json;
    }
}
