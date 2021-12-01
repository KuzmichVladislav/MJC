package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class TagDaoImpl {
    //    InitialContext initContext;
//    DataSource ds;
//    {
//        try {
//            initContext = new InitialContext();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
//    {
//        try {
//            ds = (DataSource) initContext.lookup("java:comp/env/jdbc/dbconnect");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
    private static final String URL = "jdbc:mysql://localhost:3306/gifts";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Amedab22";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Tag show(int id) {
        Tag tag = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM tag WHERE id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tag = new Tag();
            tag.setId(resultSet.getInt("id"));
            tag.setName(resultSet.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tag;
    }

    public Tag save(Tag tag) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO tag VALUES(?,?)")) {
            preparedStatement.setInt(1, tag.getId());
            preparedStatement.setString(2, tag.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tag;
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM tag WHERE id=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
