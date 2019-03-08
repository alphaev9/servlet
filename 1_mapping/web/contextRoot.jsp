<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>it's context root</h2>
<p>url is: ${url}</p>
<p>contextPath is: ${contextPath}</p>
<p>servletPath is: ${servletPath}</p>
<hr>

<p>正常写法：相对路径：相对于context（app）的根</p>
<form action="relative" method="get">
    <button type="submit">submit</button>
</form>
<hr>

<p>1、绝对路径:从主机的根(root)开始</p>
<form action="/absolute" method="get">
    <button type="submit">submit</button>
</form>
<hr>

<p>2、映射不理会参数和查询字符串</p>
<form action="withQuery?name=alpha" method="get">
    <button type="submit">submit</button>
</form>
<hr>

<p>3、通配符</p>
<form action="wildcard/alpha" method="get">
    <button type="submit">submit</button>
</form>
<hr>

<p>4、映射到Context的根目录，相对路径</p>
<form action="" method="get">
    <button type="submit">submit</button>
</form>
<hr>

<p>5、错误的请求</p>
<form action="wrong" method="get">
    <button type="submit">submit</button>
</form>

</body>
</html>
