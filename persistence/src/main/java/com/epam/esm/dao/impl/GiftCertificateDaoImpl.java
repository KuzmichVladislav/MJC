package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {
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

    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO tag VALUES(?,?,?,?,?,?,?)")) {
            preparedStatement.setInt(1, giftCertificate.getId()); // TODO: 11/30/2021 change to autoincrement
            preparedStatement.setString(2, giftCertificate.getName());
            preparedStatement.setString(3, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(4, giftCertificate.getPrice());
            preparedStatement.setInt(5, giftCertificate.getDuration());
            preparedStatement.setDate(6, (Date) giftCertificate.getCreateDate());
            preparedStatement.setDate(7, (Date) giftCertificate.getLastUpdateDate());
            // TODO: 11/30/2021 add transaction to add? tag
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificate readGiftCertificate(int id) {
        GiftCertificate giftCertificate = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM gift_certificate WHERE id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            giftCertificate = new GiftCertificate();
            giftCertificate.setId(resultSet.getInt("id"));
            giftCertificate.setName(resultSet.getString("name"));
            giftCertificate.setDescription(resultSet.getString("description"));
            giftCertificate.setPrice(resultSet.getBigDecimal("price"));
            giftCertificate.setDuration(resultSet.getInt("duration"));
            giftCertificate.setCreateDate(resultSet.getDate("create_date"));
            giftCertificate.setLastUpdateDate(resultSet.getDate("last_update_date"));
            // TODO: 11/30/2021 add tags
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificate updateGiftCertificate(int id, GiftCertificate giftCertificate) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE gift_certificate SET name=?, description=?, price=?, duration=? WHERE id=?")) {
            if (giftCertificate.getName() != null) {
                preparedStatement.setString(1, giftCertificate.getName());
            } else {
                preparedStatement.setNull(1, Types.VARCHAR);
            }
            if (giftCertificate.getDescription() != null) {
                preparedStatement.setString(2, giftCertificate.getDescription());
            } else {
                preparedStatement.setNull(2, Types.VARCHAR);
            }
            if (giftCertificate.getPrice() != null) {
                preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            } else {
                preparedStatement.setNull(3, Types.DECIMAL);
            }
            if (giftCertificate.getDuration() != 0) {
                preparedStatement.setInt(4, giftCertificate.getDuration());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return giftCertificate;
    }

    @Override
    public boolean deleteGiftCertificate(int id) {
        boolean isDelete = false;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM gift_certificate WHERE id=?")) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0){
                isDelete = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isDelete;
    }
}
