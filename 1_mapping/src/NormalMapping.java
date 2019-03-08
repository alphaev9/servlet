import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NormalMapping",urlPatterns ={"/relative","/absolute","/withQuery","/wildcard/*"})
public class NormalMapping extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*request对象封装了所有请求信息*/
        StringBuffer requestURL = request.getRequestURL();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();


        request.setAttribute("url", requestURL);
        request.setAttribute("contextPath", contextPath);
        request.setAttribute("servletPath", servletPath);

        request.getRequestDispatcher("/contextRoot.jsp").forward(request, response);
    }
}
