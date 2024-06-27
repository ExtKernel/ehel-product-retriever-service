package org.tes.productretrieverservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findFirstByOrderByCreationDateDesc();
}
