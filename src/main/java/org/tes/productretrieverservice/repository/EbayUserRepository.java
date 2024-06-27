package org.tes.productretrieverservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tes.productretrieverservice.model.EbayUser;

public interface EbayUserRepository extends JpaRepository<EbayUser, Long> {
}
