<%--
  Created by IntelliJ IDEA.
  User: 13612
  Date: 2018/10/8
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login View</title>
</head>
<body>
<form method="post" action="/login">
    账号: &nbsp;&nbsp;&nbsp;<input type="text"  name="username"/></br>
    <hr>
    密码: &nbsp;&nbsp;&nbsp;<input type="password" name="password"/></br>
    <hr>
    <input type="checkbox" name="rememberMe">记住我</input></br>
    <hr>
    <input type="submit" alt="登录">
</form>
</body>
</html>
