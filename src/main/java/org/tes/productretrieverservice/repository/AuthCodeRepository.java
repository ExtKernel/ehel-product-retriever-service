package org.tes.productretrieverservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.model.AuthCode;

import java.util.Optional;

public interface AuthCodeRepository
        extends JpaRepository<AuthCode, Long> {
    Optional<AuthCode> findFirstByOrderByCreationDateDesc();
}
