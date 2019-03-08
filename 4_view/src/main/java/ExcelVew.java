import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ExcelVew", urlPatterns = "/excel")
public class ExcelVew extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("alpha", "computer science"));
        list.add(new User("laskin", "math"));
        list.add(new User("apple", "machanical engineering"));

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("info");
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("name");
        header.createCell(1).setCellValue("major");

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getMajor());
        }

        /*Content-Disposition指示浏览器下载文件*/
        response.setHeader("Content-Disposition", "attachment;filename=user.xls");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
    }
}
