package com.library.dao;

import com.library.config.DatabaseConfig;
import com.library.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // CREATE
    public void insert(Book book) throws SQLException {
        String sql = "INSERT INTO books (book_id, title, author, genre, publisher, year_published, isbn, quantity, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getBookId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getGenre());
            stmt.setString(5, book.getPublisher());
            stmt.setInt(6, book.getYearPublished());
            stmt.setString(7, book.getIsbn());
            stmt.setInt(8, book.getQuantity());
            stmt.setString(9, book.getStatus());
            stmt.executeUpdate();
        }
    }

    // READ ALL
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        }
        return books;
    }

    // READ with SEARCH
    public List<Book> search(String keyword) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ? ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String kw = "%" + keyword.toLowerCase() + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) books.add(mapRow(rs));
            }
        }
        return books;
    }

    // UPDATE
    public void update(Book book) throws SQLException {
        String sql = "UPDATE books SET title=?, author=?, genre=?, publisher=?, year_published=?, " +
                     "isbn=?, quantity=?, status=? WHERE book_id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getGenre());
            stmt.setString(4, book.getPublisher());
            stmt.setInt(5, book.getYearPublished());
            stmt.setString(6, book.getIsbn());
            stmt.setInt(7, book.getQuantity());
            stmt.setString(8, book.getStatus());
            stmt.setString(9, book.getBookId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(String bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookId);
            stmt.executeUpdate();
        }
    }

    // Get the next available Book ID (e.g., BK-021)
    public String getNextBookId() throws SQLException {
        String sql = "SELECT book_id FROM books ORDER BY id DESC LIMIT 1";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastId = rs.getString("book_id"); // e.g., "BK-020"
                int num = Integer.parseInt(lastId.split("-")[1]);
                return String.format("BK-%03d", num + 1);
            }
        }
        return "BK-001";
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        return new Book(
            rs.getInt("id"),
            rs.getString("book_id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("genre"),
            rs.getString("publisher"),
            rs.getInt("year_published"),
            rs.getString("isbn"),
            rs.getInt("quantity"),
            rs.getString("status")
        );
    }
}
