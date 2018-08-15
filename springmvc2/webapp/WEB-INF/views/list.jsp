<%--
  Created by IntelliJ IDEA.
  User: libingshen
  Date: 2018/8/4
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="scripts/jquery-3.3.1.js"></script>
<body>

<%--删除一个员工默认为get请求，将get请求转换为post请求--%>
<form action="" method="post">
    <input type="hidden" name="_method" value="DELETE">
</form>
<c:if test="${empty requestScope.employees}">
    没有任何员工信息
</c:if>
<c:if test="${!empty requestScope.employees}">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>Id</th>
            <th>LastName</th>
            <th>Email</th>
            <th>Gender</th>
            <th>Department</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${requestScope.employees}" var="emp">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.lastName}</td>
                <td>${emp.email}</td>
                <td>${emp.gender==0?'Female':'Male'}</td>
                <td>${emp.department.departmentName}</td>
                <td><a href="#">Edit</a></td>
                <td><a class="delete" href="/emp/${emp.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<br><br>
<a href="emp">Add New Employee</a>

<script>
    var log = function () {
        console.log.apply(console, arguments)
    }

    //删除员工
    var deleteEvent = function () {
        $(".delete").on("click", function () {
            log("点击删除按钮。。。")
            var href_value = $(this).attr("href")
            log('==========>',href_value)
            $("form").attr("action", href_value).submit()
            return false
        })
    }
    var bindEvents = function () {
        deleteEvent()
    }

    var __main = function () {
        bindEvents()
    }


    $(function () {
        log("页面加载完成")
        __main()
    })
</script>

</body>
</html>
