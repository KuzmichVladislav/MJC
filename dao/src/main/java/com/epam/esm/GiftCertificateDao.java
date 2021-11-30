package com.epam.esm;

import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateDao {
    //    InitialContext initContext;
//    DataSource ds;
//
//
//    {
//        try {
//            initContext = new InitialContext();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
//
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

    public List<Tag> index() {
        List<Tag> tagList = new ArrayList<>();
        String SQL = "SELECT * FROM tag";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt("id"));
                tag.setName(resultSet.getString("name"));
                tagList.add(tag);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(tagList);
        return tagList;
    }

    public void save(Tag tag) {
        String SQL = "INSERT INTO tag VALUES(" + 1 + ",'" + tag.getName() + "')";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
