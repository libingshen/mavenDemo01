<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: libingshen
  Date: 2018/8/12
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<%--
     1、为什么要使用form标签？
     可以更快速地开发出表单页面，而且可以更方便的进行表单值的回显
     2. 注意:
		可以通过 modelAttribute 属性指定绑定的模型属性,
		若没有指定该属性，则默认从 request 域对象中读取 command 的表单 bean
		如果该属性值也不存在，则会发生错误。

--%>

<form:form action="emp" method="post" modelAttribute="employee">
    <%--path对应HTML表单标签的name属性值--%>
    lastName:<form:input path="lastName"/>
    <br/>
    Email:<form:input path="email"/>
    <br>
    <%
        Map<String, String> genders = new HashMap();
        genders.put("1", "Male");
        genders.put("0", "Female");

        request.setAttribute("genders", genders);
    %>
    Genders:<form:select path="gender" items="${genders}"/>
    <br/>
    Depatment:<form:select path="department.id" items="${departments}" itemLabel="departmentName" itemValue="id"/>
    <br/>
    <input type="submit" value="submit">
</form:form>
</body>
</html>
