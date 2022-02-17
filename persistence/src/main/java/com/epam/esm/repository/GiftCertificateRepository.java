package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface GiftCertificateRepository for {@link GiftCertificate}s.
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, GiftCertificateRepositoryJpa {

    /**
     * Exists by tags' identifier boolean.
     *
     * @param id the tags identifier
     * @return the boolean
     */
    boolean existsByTags_Id(long id);
}
