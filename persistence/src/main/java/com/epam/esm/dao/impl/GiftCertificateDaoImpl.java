package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(GiftCertificate giftCertificate) {
        String SQL = "INSERT INTO gift_certificate (name, description, price, duration) VALUES(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL, new String[]{"id"});
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            preparedStatement.setInt(4, giftCertificate.getDuration());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public GiftCertificate read(int id) {
        return jdbcTemplate.query("SELECT * FROM gift_certificate WHERE id=?", new BeanPropertyRowMapper<>(GiftCertificate.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public GiftCertificate update(int id, GiftCertificate giftCertificate) {
        jdbcTemplate.update("UPDATE gift_certificate SET name=?, description=?, price=?, duration=? WHERE id=?", id);
        return giftCertificate;
//        try (PreparedStatement preparedStatement =
//                     connection.prepareStatement("UPDATE gift_certificate SET name=?, description=?, price=?, duration=? WHERE id=?")) {
//            if (giftCertificate.getName() != null) {
//                preparedStatement.setString(1, giftCertificate.getName());
//            } else {
//                preparedStatement.setNull(1, Types.VARCHAR);
//            }
//            if (giftCertificate.getDescription() != null) {
//                preparedStatement.setString(2, giftCertificate.getDescription());
//            } else {
//                preparedStatement.setNull(2, Types.VARCHAR);
//            }
//            if (giftCertificate.getPrice() != null) {
//                preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
//            } else {
//                preparedStatement.setNull(3, Types.DECIMAL);
//            }
//            if (giftCertificate.getDuration() != 0) {
//                preparedStatement.setInt(4, giftCertificate.getDuration());
//            } else {
//                preparedStatement.setNull(4, Types.INTEGER);
//            }
//            preparedStatement.setInt(5, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return giftCertificate;
    }

    @Override
    public boolean delete(int id) {
        boolean isDelete = false;
        if (jdbcTemplate.update("DELETE FROM gift_certificate WHERE id=?", id) > 0) {
            isDelete = true;
        }
        return isDelete;
    }

    //        boolean isDelete = false;
//        try (PreparedStatement preparedStatement =
//                     connection.prepareStatement("DELETE FROM gift_certificate WHERE id=?")) {
//            preparedStatement.setInt(1, id);
//            if (preparedStatement.executeUpdate() > 0) {
//                isDelete = true;
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return isDelete;
//    }
    public List<GiftCertificate> readAllCertificateByTagId(int tagId) {
        return jdbcTemplate.query("SELECT id, name, description, price, duration, createDate, lastUpdateDate, giftCertificate, tag\n" +
                        "FROM gift_certificate\n" +
                        "   LEFT JOIN gift_certificate_tag_include gcti on gift_certificate.id = gcti.giftCertificate\n" +
                        "WHERE gcti.tag = ?",
                new BeanPropertyRowMapper<>(GiftCertificate.class), tagId);
    }

}
