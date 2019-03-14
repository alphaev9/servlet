package com.alpha.dao;

import com.alpha.component.DataSource;
import com.alpha.annotation.Dao;
import com.alpha.annotation.Inject;
import com.alpha.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Dao
public class BookDao {

    @Inject
    private static DataSource dataSource;

    private static Connection getConnection() {
        return dataSource.getConnection();
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
