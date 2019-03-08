<%@ page contentType="text/html;charset=gbk" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--  <script src="jquery.js"></script>
      <script src="jsencrypt.js"></script>
      <script src="encrypt.js"></script>--%>
</head>
<body>
<h1>get方法请求</h1>
<form action="test" method="get" id="form1">
    <input name="nickName" autofocus><br>
    <input name="password"><br>
    <button type="submit">submit</button>
</form>
<hr>
<h1>post方法请求</h1>
<form action="test" method="post" id="form2">
    <input name="nickName" autofocus><br>
    <input name="password"><br>
    <button type="submit">submit</button>
</form>

</body>
</html>
