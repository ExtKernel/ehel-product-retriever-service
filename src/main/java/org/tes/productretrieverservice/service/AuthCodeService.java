package org.tes.productretrieverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tes.productretrieverservice.exception.NoRecordOfAuthCodeException;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.repository.AuthCodeRepository;

@Service
public class AuthCodeService extends GenericCrudService<AuthCode, Long> {
    AuthCodeRepository repository;

    @Autowired
    public AuthCodeService(
            AuthCodeRepository repository
    ) {
        super(repository);
        this.repository = repository;
    }

    /**
     * Retrieves the latest {@link AuthCode} object saved in the database.
     *
     * @return the latest {@link AuthCode} object saved in the database.
     * @throws NoRecordOfAuthCodeException if there are no {@link AuthCode} objects in the database.
     */
    public AuthCode findLatest()
            throws NoRecordOfAuthCodeException {
        if (repository.findFirstByOrderByCreationDateDesc().isPresent()) {
            return repository.findFirstByOrderByCreationDateDesc().get();
        } else {
            throw new NoRecordOfAuthCodeException(
                            "The latest saved authorization code was not found, "
                                    + "because no record exists in the database"
            );
        }
    }
}
