package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface TagRepository for {@link Tag}s.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Find by tag name.
     *
     * @param name the name of tag
     * @return the optional of tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Find most widely used tag of a user with the highest cost of all orders.
     *
     * @param id the identifier
     * @return the optional
     */
    @Query(value = "SELECT id, name\n" +
            "         FROM (\n" +
            "         SELECT id, name, sum(cost) AS amount, (COUNT(id) * number_of_certificates) AS number_of_orders\n" +
            "         FROM (\n" +
            "                  SELECT t.id,\n" +
            "                         t.name,\n" +
            "                         (oc.gift_certificate_cost * oc.number_of_certificates) AS 'cost',\n" +
            "                         oc.number_of_certificates\n" +
            "                  FROM tag t\n" +
            "                           LEFT JOIN gift_certificate_has_tag gcti ON t.id = gcti.tag_id\n" +
            "                           LEFT JOIN gift_certificate gc ON gc.id = gcti.gift_certificate_id\n" +
            "                           LEFT JOIN order_certificates oc ON gc.id = oc.gift_certificate_id\n" +
            "                           LEFT JOIN orders o ON oc.order_id = o.id\n" +
            "                           LEFT JOIN user u ON o.user_id = u.id\n" +
            "                  WHERE u.id = ?1) AS inner_table\n" +
            "         GROUP BY id\n" +
            "         ORDER BY number_of_orders DESC, amount DESC\n" +
            "         LIMIT 1) AS outer_table",
            nativeQuery = true)
    Optional<Tag> findMostUsedUserTag(long id);
}
