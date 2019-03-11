package com.alpha.dao;

import com.alpha.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/books?characterEncoding=utf8", "root", "123456");
        return connection;
    }

    public static List<Book> queryByAuthor(String author) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT title,press FROM books.book WHERE author=?");
        ps.setString(1, author);
        ResultSet rs = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            String title = rs.getString("title");
            String press = rs.getString("press");
            book.setTitle(title);
            book.setPress(press);
            books.add(book);
        }
        return books;
    }
}
