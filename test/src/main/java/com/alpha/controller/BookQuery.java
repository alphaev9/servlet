package com.alpha.controller;

import com.alpha.dao.BookDao;
import com.alpha.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookQuery", urlPatterns = "/query")
public class BookQuery extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String author = request.getParameter("author");
        try {
            List<Book> books = BookDao.queryByAuthor(author);
            request.setAttribute("author",author);
            request.setAttribute("books", books);
            request.setAttribute("display", "show");
            List<String> heads = new ArrayList();
            heads.add("Title");
            heads.add("Press");
            request.setAttribute("heads", heads);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

