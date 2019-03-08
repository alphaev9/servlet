import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "JsonView",urlPatterns = "/json")
public class JsonView extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("alpha","computer science"));
        list.add(new User("laskin","math"));
        list.add(new User("lisa","machanical engineering"));
        Gson gson = new Gson();
        String s = gson.toJson(list);
        response.setHeader("Content-type"," application/json");
        response.getWriter().write(s);
    }
}
