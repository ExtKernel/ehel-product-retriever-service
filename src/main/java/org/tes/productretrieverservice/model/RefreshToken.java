package org.tes.productretrieverservice.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class RefreshToken extends Token {

    public RefreshToken(
            String token,
            int expiresIn
    ) {
        super(
                token,
                expiresIn
        );
    }
}
