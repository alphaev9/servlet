import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PdfView", urlPatterns = "/pdf")
public class PdfView extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("alpha", "computer science"));
        list.add(new User("laskin", "math"));
        list.add(new User("apple", "machanical engineering"));

        Document document = new Document();
        response.setContentType("application/pdf");
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Font font = new Font();
            font.setSize(26);
            font.setStyle(Font.BOLD);
            paragraph.setFont(font);
            paragraph.add("User Info");
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(2);

            table.addCell("name");
            table.addCell("major");
            table.completeRow();

            list.forEach(
                    user -> {
                        table.addCell(user.getName());
                        table.addCell(user.getMajor());
                        table.completeRow();
                    }
            );
            document.add(table);


        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

    }
}
