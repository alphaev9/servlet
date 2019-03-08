import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从表单获得的全部都是字符串类型
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String[] hobbies = request.getParameterValues("hobby");
        //类型转换：从html的字符串世界——到java的类型世界
        Gender enum_gender = Gender.valueOf(gender);
        SimpleDateFormat sdf=new SimpleDateFormat("yy-mm-dd");
        Date date_birthday=null;
        try {
            date_birthday= sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> list_hobbys = Arrays.asList(hobbies);
        User user = new User(name, password, enum_gender, date_birthday, list_hobbys);

        //基于User类型进行使用，比如：传递对象等
        request.setAttribute("user",user);
        request.getRequestDispatcher("userInfo.jsp").forward(request,response);

        /******************************
         * 框架介入点：
         * 1、前端数据自动绑定，获取
         * 2、类型转换自动化：自定义类型由程序员提供Convert
         *
         *********************************/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
