<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>

<h1>1、通过表单的方式：单个表单域</h1>
<form action="singleFeild" method="get">
    <input name="user" placeholder="please input your name" autofocus>
    <button type="submit">submit</button>
</form>
<hr>

<h1>2、通过表单的方式：多个表单域</h1>
<form action="mutiFeild" method="post">
    <label>please select your hobby: </label></br>
    <input type="checkbox" name="hobby" value="sport">sport</input>
    <input type="checkbox" name="hobby" value="reading">reading</input>
    <input type="checkbox" name="hobby" value="movie">movie</input>
    <button type="submit">submit</button>
</form>
<hr>

<h1>3、用报文正文</h1>
<%-----------------------------------
1、enctype属性控制报文的编码
2、默认是application/x-www-form-urlencoded，修改成其他，就不能用Request对象的API直接解析
4、文件以二进制的形式放在报文里
------------------------------------%>
<form action="uploadFile" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input name="path" placeholder="input your file save location">
    <button type="submit">upload</button>
</form>
<hr>

<h1>4、通过URL编码</h1>
<a href="order?id=3">Coded by query string："order?id=3"</a><br>
<a href="order/id/3">Coded by path："order/id/3"</a>
<hr>

<h1>5、content-type</h1>
<a id="submit" href="#">request via json format</a>
<div id="result"></div>
<script src="request.js"></script>
</body>
</html>
