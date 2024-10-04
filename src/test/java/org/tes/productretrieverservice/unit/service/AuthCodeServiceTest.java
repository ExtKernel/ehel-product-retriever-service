package org.tes.productretrieverservice.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tes.productretrieverservice.TestFactory;
import org.tes.productretrieverservice.model.AuthCode;
import org.tes.productretrieverservice.repository.AuthCodeRepository;
import org.tes.productretrieverservice.service.AuthCodeService;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthCodeServiceTest extends TestFactory {

    @Mock
    private AuthCodeRepository authCodeRepository;

    @InjectMocks
    private AuthCodeService authCodeService;

    @Test
    public void givenValidAuthCode_whenGetValid_thenReturnValidAuthCode() {
        AuthCode authCode = buildValidAuthCode();
        authCode.setCreationDate(new Date());

        when(authCodeRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.of(authCode));

        assertEquals(authCode, authCodeService.getValid());
    }

    @Test
    public void givenLatestAuthCode_whenGetLatest_thenReturnLatestAuthCode() {
        AuthCode authCode = buildValidAuthCode();
        authCode.setCreationDate(new Date());

        when(authCodeRepository.findFirstByOrderByCreationDateDesc()).thenReturn(Optional.of(authCode));

        assertEquals(authCode, authCodeService.findLatest());
    }
}
