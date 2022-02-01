package com.epam.esm.repository;

import com.epam.esm.entity.OrderCertificateDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface OrderCertificateDetailsRepository for {@link OrderCertificateDetails}s.
 */
public interface OrderCertificateDetailsRepository extends JpaRepository<OrderCertificateDetails, Long> {
}
