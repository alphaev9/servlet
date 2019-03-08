import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "CustomErrorHandler", urlPatterns = "/*")
public class CustomErrorHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String pathInfo = request.getPathInfo();

        DispatcherType dispatcherType = request.getDispatcherType();
        if (dispatcherType.compareTo(DispatcherType.FORWARD) == 0 || dispatcherType.compareTo(DispatcherType.REQUEST) == 0) {
            if (pathInfo.endsWith("/")) {
                request.getRequestDispatcher("index.html").forward(request, response);
            } else if (pathInfo.endsWith("jsp") || pathInfo.endsWith("jspx")) {
                context.getNamedDispatcher("CostumJspServlet").forward(request, response);
            } else if (pathInfo.endsWith("html") || pathInfo.endsWith("jpg") || pathInfo.endsWith("css")) {
                context.getNamedDispatcher("default").forward(request, response);
            } else {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            /*include的情况还没有解决*/
            context.getNamedDispatcher("jsp").include(request, response);
        }
    }
}
