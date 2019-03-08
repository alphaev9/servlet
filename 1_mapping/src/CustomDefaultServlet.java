import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/************************************
 * 1、缺省Servlet负责匹配所有高优先级匹配规则匹配不到的url
 * 2、可处理静态资源：在Web应用的根目录下查找对应的文件，如果存在，把文件内容写到输出流，如果不存在，返回404
 * 3、可以覆盖掉缺省Servlet,但处理不好很容易造成StackOverflow，
 * */
@WebServlet(name = "CustomDefaultServlet", urlPatterns = "/")
public class CustomDefaultServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();

        PrintWriter writer = response.getWriter();
        writer.write("<h1>Processed by CustomDefaultServlet........ </h1>");
        writer.write("<hr>");

        context.getNamedDispatcher("default").include(request, response);
    }
}
