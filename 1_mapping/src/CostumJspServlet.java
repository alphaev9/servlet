import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*************
 * 缺省的jsp Servlet负责jsp或者jspx后缀文件的compiler和execution
 * */
@WebServlet(name = "CostumJspServlet", urlPatterns = "*.jsp")
public class CostumJspServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        下面的代码会造成死循环，导致StackOverflow
//        request.getRequestDispatcher("/contextRoot.jsp").forward(request, response);


        ServletContext context = getServletContext();

        PrintWriter writer = response.getWriter();
        writer.write("<h1>Processed by CustomJspServlet........ </h1>");
        writer.write("<hr>");

        context.getNamedDispatcher("jsp").include(request, response);

    }
}
