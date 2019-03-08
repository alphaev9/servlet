import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GenerateView", urlPatterns = "/generateView/*")
public class GenerateView extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", "alpha");

        PrintWriter writer = response.getWriter();
        writer.write("<h1>dispatch request to a jsp component</h1>");
        String pathInfo = request.getPathInfo();
        if ("/forward".equals(pathInfo)) {
            request.getRequestDispatcher("/jspView.jsp").forward(request, response);
            writer.write("<h1>forward done!</h1>");
        } else if ("/include".equals(pathInfo)) {
            /*发送给被include的资源的request对象不变*/
            request.getRequestDispatcher("/jspView.jsp").include(request, response);
            writer.write("<h1>forward done!</h1>");
        } else {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
