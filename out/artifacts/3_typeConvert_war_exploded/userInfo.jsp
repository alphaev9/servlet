<%--

--%>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>User information</h1>
<p>name:${user.name}</p>
<p>password:${user.password}</p>
<p>gender:${user.gender}</p>
<p>birthday:${user.birthday}</p>
<p>hobbys:
    <c:forEach items="${user.hobbys}" var="hobby">
        <span>${hobby}&nbsp;</span>
    </c:forEach>
</p>
</body>
</html>
