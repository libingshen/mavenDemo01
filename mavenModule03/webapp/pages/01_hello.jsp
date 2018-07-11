<%--
  Created by IntelliJ IDEA.
  User: libingshen
  Date: 2018/4/11
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/" />--%>

<jsp:include page="/pages/comm/base01.jsp"></jsp:include>
<h1>我是hello.jsp</h1>
<form action="HelloServlet" METHOD="post">
    <input type="text" name="info" id="info">
    <input type="submit" value="提交">
</form>



</body>
</html>
