import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "GetRequestInfo", urlPatterns = {"/singleFeild", "/mutiFeild", "/uploadFile", "/order/*","/content"})
@MultipartConfig()
public class GetRequestInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/singleFeild":
                getSingleFormFeild(request, response);
                break;
            case "/mutiFeild":
                getMutiFormFeild(request, response);
                break;
            case "/uploadFile":
                handleUploadFile(request, response);
                break;
            case "/order":
                getInfoViaURL(request, response);
                break;
            case "/content":
                getMessageViaContentType(request, response);
        }
    }

    private void getSingleFormFeild(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("user");

        response.setHeader("Content-type", "text/html;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write("<h1>user name is:" + user + "</h1>");
    }

    private void getMutiFormFeild(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] hobbies = request.getParameterValues("hobby");
        PrintWriter writer = response.getWriter();
        writer.write("<h1>hobbies include: </h1>");
        for (String hobby : hobbies) {
            writer.write("<p>" + hobby + "</p>");
        }
    }

    private void handleUploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String path = request.getParameter("path");
        String root = getServletContext().getRealPath("/");
//      在Web App的根目录下创建目录
        String saveLocation = root + "//" + path;
        File file = new File(saveLocation);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        filePart.write(root + "//" + path + "//" + fileName);
        PrintWriter writer = response.getWriter();
        writer.write("<h1>Uploaded successfully!</h1>");
    }

    private void getInfoViaURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String queryString = request.getQueryString();
        String id;
        if (queryString != null) {
            id = request.getParameter("id");
        } else {
            String pathInfo = request.getPathInfo();
            int i = pathInfo.lastIndexOf('/');
            id = pathInfo.substring(i + 1);
        }
        PrintWriter writer = response.getWriter();
        writer.write("<h1>order id is:" + id + "</h1>");
    }

    private void getMessageViaContentType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder text = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line);
        }
        Gson gson = new Gson();
        Book book = gson.fromJson(text.toString(), Book.class);

        response.getOutputStream().write(book.toString().getBytes());
        response.setContentType("text/myFormat");
        response.setCharacterEncoding("utf-8");
        System.out.println(book.getAuthor());
    }
}
