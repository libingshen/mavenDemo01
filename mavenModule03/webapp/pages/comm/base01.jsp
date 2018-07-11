<%--
  Created by IntelliJ IDEA.
  User: libingshen
  Date: 2018/4/11
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>我是base01.jsp</h1>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/" />
</body>
</html>
