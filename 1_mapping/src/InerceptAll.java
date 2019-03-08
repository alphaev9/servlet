import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**********************
 * 是路径匹配：优先级低于精确匹配,高于后缀匹配和缺省匹配
 * */
@WebServlet(name = "InerceptAll", urlPatterns = "/*")
public class InerceptAll extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*******************
         * 1、必须要设置编码，因为后面的资源是被include进来的，也就是用这里的writer把包含的资源写到响应包
         * 2、必须要在response.getWriter前设置，否则无效
         * */
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        ServletContext context = getServletContext();
        String pathInfo = request.getPathInfo();

        /*******************************
         * 1、ServletPath是用于和servlet匹配的部分
         * 2、pathInfo是匹配到servlet后，除过ServletPath剩余的部分
         * */
        writer.write("<h1>intercepted! " + "  request is：   " + pathInfo + "</h1><hr>");
        if (pathInfo.matches(".*\\.jsp")) {
            context.getNamedDispatcher("CostumJspServlet").include(request, response);
        } else if (pathInfo.matches(".*\\.html")) {
            context.getNamedDispatcher("CustomDefaultServlet").include(request, response);
        } else {
            request.getRequestDispatcher("error.html").forward(request, response);
        }
    }
}
