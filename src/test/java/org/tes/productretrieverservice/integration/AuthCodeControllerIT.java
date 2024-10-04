package org.tes.productretrieverservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.tes.productretrieverservice.model.AuthCode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AuthCodeControllerIT extends EbayAbstractIntegrationTest {

    @Test
    public void givenCodeAndExpiredIn_whenSave_thenReturnAuthCode()
            throws Exception {
        AuthCode authCode = buildValidAuthCode();
        String uri = buildSaveAuthCodeUri(authCode);

        // test 200 OK and get the result
        AuthCode result = performGetRequestExpectedSuccess(
                uri,
                AuthCode.class
        );
        // check the result itself
        assertEquals(authCode.getAuthCode(), result.getAuthCode());
        assertEquals(authCode.getExpiresIn(), result.getExpiresIn());
    }

    @Test
    public void givenSavedAuthCodes_whenFindLatest_thenReturnLatestSavedAuthCodes()
            throws Exception {
        AuthCode authCode1 = buildValidAuthCode();
        authCode1.setId(1L);
        AuthCode authCode2 = buildValidAuthCode();
        authCode2.setId(2L);
        AuthCode authCode3 = buildValidAuthCode();
        authCode3.setId(3L);

        List<AuthCode> authCodes = new ArrayList<>();
        authCodes.add(authCode1);
        authCodes.add(authCode2);
        authCodes.add(authCode3);

        // save auth codes
        authCodes.forEach(authCode -> {
            String uri = buildSaveAuthCodeUri(authCode);
            try {
                performGetRequest(
                        uri,
                        AuthCode.class,
                        status().is2xxSuccessful()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // test 200 OK and get the result
        AuthCode result = performGetRequestExpectedSuccess(
                "/secured/auth-code/latest",
                AuthCode.class
        );
        // check the result itself
        assertEquals(authCode3.getAuthCode(), result.getAuthCode());
        assertEquals(authCode3.getExpiresIn(), result.getExpiresIn());
    }
}
