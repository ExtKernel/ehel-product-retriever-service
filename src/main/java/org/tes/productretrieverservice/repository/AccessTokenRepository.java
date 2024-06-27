package org.tes.productretrieverservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.model.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findFirstByOrderByCreationDateDesc();
}
