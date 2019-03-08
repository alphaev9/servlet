<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
     <form action="register" method="post">
       <label>name</label>
       <input name="name"><br>
       <label>password</label>
       <input name="password"><br>
       <input type="radio" name="gender" value="male">male
       <input type="radio" name="gender" value="female">female<br>
       <input type="date" name="birthday"><br>
       <input type="checkbox" name="hobby" value="sport">sport
       <input type="checkbox" name="hobby" value="reading">reading
       <input type="checkbox" name="hobby" value="movie">movie<br>
       <button type="submit">register</button>
     </form>
  </body>
</html>
