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
    @Query(value = """
            SELECT id, name
                     FROM (
                     SELECT id, name, sum(cost) AS amount, (COUNT(id) * number_of_certificates) AS number_of_orders
                     FROM (
                              SELECT t.id,
                                     t.name,
                                     (oc.gift_certificate_cost * oc.number_of_certificates) AS 'cost',
                                     oc.number_of_certificates
                              FROM tag t
                                       LEFT JOIN gift_certificate_has_tag gcti ON t.id = gcti.tag_id
                                       LEFT JOIN gift_certificate gc ON gc.id = gcti.gift_certificate_id
                                       LEFT JOIN order_certificates oc ON gc.id = oc.gift_certificate_id
                                       LEFT JOIN orders o ON oc.order_id = o.id
                                       LEFT JOIN user u ON o.user_id = u.id
                              WHERE u.id = ?1) AS inner_table
                     GROUP BY id
                     ORDER BY number_of_orders DESC, amount DESC
                     LIMIT 1) AS outer_table""",
            nativeQuery = true)
    Optional<Tag> findMostUsedUserTag(long id);
}
