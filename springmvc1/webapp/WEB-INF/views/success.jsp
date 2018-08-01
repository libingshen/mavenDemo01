<%--
  Created by IntelliJ IDEA.
  User: libingshen
  Date: 2018/7/29
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--测试国际化--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>success page</h1>


<%--目标方法的返回值可以是 ModelAndView 类型--%>
time: ${requestScope.time }
<br><br>

<%--目标方法可以添加 Map 类型--%>
names: ${requestScope.names }
<br><br>

<%--默认放在request域中--%>
request user: ${requestScope.user }
<br><br>

session user: ${sessionScope.user }
<br><br>

request school: ${requestScope.school }
<br><br>

session school: ${sessionScope.school }
<br><br>


<%--abc user: ${requestScope.abc }
<br><br>

abc user: ${requestScope.abc }--%>

user: ${requestScope.user }
<br><br>


<fmt:message key="i18n.username"></fmt:message>
<br><br>

<fmt:message key="i18n.password"></fmt:message>
<br><br>



</body>
</html>
