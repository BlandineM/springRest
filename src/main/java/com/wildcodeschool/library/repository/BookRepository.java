package com.wildcodeschool.library.repository;


import com.wildcodeschool.library.entity.Book;
import com.wildcodeschool.library.utills.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class BookRepository implements CrudDao<Book> {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/Library?serverTimezone=GMT";
    private final static String DB_USER = "toto";
    private final static String DB_PASSWORD = "Titi123!";

    @Override
    public Book save(Book book){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try{
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            statement= connection.prepareStatement(
                    "INSERT INTO Book (title, author, description) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getDescription());

            if (statement.executeUpdate() !=1){
                throw new SQLException("Failed to insert data");
            }
            generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()){
                Long id = generatedKeys.getLong(1);
                book.setId(id);
                return book;
            } else {
                throw new SQLException("Failed to get inserted id");
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeResultSet(generatedKeys);
        }
        return null;
    }

    @Override
    public List<Book> searchBook(String searchTerm) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );

            statement = connection.prepareStatement(
                    "SELECT * FROM Book WHERE title LIKE ? OR description LIKE ?;"
            );
            statement.setString(1, "%"+searchTerm+"%");
            statement.setString(2, "%"+searchTerm+"%");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                 books.add(new Book(id, title, author, description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return books;

    }

    @Override
    public Book findById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            statement = connection.prepareStatement(
                    "SELECT * FROM Book WHERE id = ?;"
            );
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                String title = resultSet.getString("name");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                return new Book(id, title, author, description);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            statement = connection.prepareStatement(
                    "SELECT * FROM Book;"
            );
            resultSet = statement.executeQuery();

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                books.add(new Book(id, title, author, description));
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Book update(Book book) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            statement = connection.prepareStatement(
                    "UPDATE Book SET title=?, author=?, description=? WHERE id=?"
            );
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getDescription());
            statement.setLong(4, book.getId());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to update data");
            }
            return book;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            statement = connection.prepareStatement(
                    "DELETE FROM Book WHERE id=?"
            );
            statement.setLong(1, id);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to delete data");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }
}
