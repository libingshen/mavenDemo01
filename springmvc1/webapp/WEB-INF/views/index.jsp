<%--
  Created by IntelliJ IDEA.
  User: libingshen
  Date: 2018/7/30
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>index...</h1>

<a href="springmvc/testServletAPI">Test ServletAPI</a>
<br><br>

<%--测试表单参数封装成实体类，name和实体类的属性名称一致，支持级联属性--%>
<form action="springmvc/testPojo" method="post">
    username: <input type="text" name="username"/>
    <br>
    password: <input type="password" name="password"/>
    <br>
    email: <input type="text" name="email"/>
    <br>
    age: <input type="text" name="age"/>
    <br>
    city: <input type="text" name="address.city"/>
    <br>
    province: <input type="text" name="address.province"/>
    <br>
    <input type="submit" value="Submit"/>
</form>
<br><br>

<a href="springmvc/testCookieValue">Test CookieValue</a>
<br><br>

<a href="springmvc/testRequestHeader">Test RequestHeader</a>
<br><br>

<a href="springmvc/testRequestParam?username=atguigu&age=11">Test RequestParam</a>
<br><br>

<form action="springmvc/testRest/1" method="post">
    <input type="hidden" name="_method" value="PUT"/>
    <input type="submit" value="TestRest PUT"/>
</form>
<br><br>

<%--测试：将post请求转成delete请求--%>
<form action="springmvc/testRest/1" method="post">
    <input type="hidden" name="_method" value="DELETE"/>
    <input type="submit" value="TestRest DELETE"/>
</form>
<br><br>

<form action="/springmvc/testRest" method="post">
    <input type="submit" value="TestRest POST"/>
</form>
<br><br>

<a href="springmvc/testRest/1">Test Rest Get</a>
<br><br>

<a href="/springmvc/testPathVariable/1">testPathVariable</a><br><br>

<a href="/springmvc/testAntPath/*/abc">testAntPath</a><br><br>

<a href="/springmvc/testParamsAndHeaders">testParamsAndHeaders</a><br><br>

<form action="/springmvc/testMethod" method="post">
    <input type="submit" value="testMethod">
</form>


<a href="/springmvc/testRequestMapping">testRequestMapping</a><br><br>

<a href="/helloworld">hello</a><br><br>
</body>
</html>
