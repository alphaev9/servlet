import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "XmlView", urlPatterns = "/xml")
public class XmlView extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("alpha", "computer science"));
        list.add(new User("laskin", "math"));
        list.add(new User("lisa", "machanical engineering"));

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-type", " application/xml");
            /*如何序列化List，能否不用包装类，此处是错的*/
            marshaller.marshal(list, outputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
