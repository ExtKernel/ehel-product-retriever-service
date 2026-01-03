package org.tes.productretrieverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tes.productretrieverservice.exception.ExpiredAuthCodeException;
import org.tes.productretrieverservice.exception.NoRecordOfAuthCodeException;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.repository.AuthCodeRepository;

import java.time.Instant;

@Service
public class AuthCodeService extends GenericCrudService<AuthCode, Long> {
    private final AuthCodeRepository repository;

    @Autowired
    public AuthCodeService(
            AuthCodeRepository repository
    ) {
        super(repository);
        this.repository = repository;
    }

    /**
     * Get a {@link AuthCode}, which will be checked for validity.
     *
     * @return the latest {@link AuthCode}.
     * @throws ExpiredAuthCodeException if the latest {@link AuthCode} is expired.
     */
    public AuthCode getValid() {
        AuthCode authCode = findLatest();
        Instant expiration = authCode.getCreationDate().toInstant().plusSeconds(authCode.getExpiresIn());

        if (Instant.now().isAfter(expiration)) throw new ExpiredAuthCodeException(
                "The auth code with id " + authCode.getId() + " and creation timestamp " + authCode.getCreationDate().toString() + " has expired."
                        + " It was valid for "
                        + authCode.getExpiresIn()
        );

        return authCode;
    }

    /**
     * Retrieves the latest {@link AuthCode} object saved in the database.
     * Which might be expired!
     *
     * @return the latest {@link AuthCode} object saved in the database.
     * @throws NoRecordOfAuthCodeException if there are no {@link AuthCode} objects in the database.
     */
    public AuthCode findLatest()
            throws NoRecordOfAuthCodeException {
        return repository.findFirstByOrderByCreationDateDesc().orElseThrow(() -> new NoRecordOfAuthCodeException(
                "There is no record of auth codes in the database"
        ));
    }
}
