package org.tes.productretrieverservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.tes.productretrieverservice.ProductRetrieverServiceApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.tes.productretrieverservice.TestUtils.obtainKeycloakAccessToken;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ProductRetrieverServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRetrieverControllerIT {

    @Autowired
    private MockMvc mvc;

    @Value("${oauthTestAdminUsername}")
    private String oauthTestAdminUsername;

    @Value("${oauthTestAdminPassword}")
    private String oauthTestAdminPassword;

    @Value("${tokenEndpointUrl}")
    private String tokenEndpointUrl;

    @Value("${testKeyword}")
    private String testKeyword;

    @Value("${testSort}")
    private String testSort;

    @Value("${testFilters}")
    private String testFilters;

    private static List<String> filtersList;

    private static String accessToken;

    @BeforeAll
    public void setUpTestFilters() {
        filtersList = List.of(testFilters.split(", "));
    }

    @BeforeEach
    public void setUpOauth() throws Exception {
        accessToken = obtainKeycloakAccessToken(
                oauthTestAdminUsername,
                oauthTestAdminPassword,
                tokenEndpointUrl
        );
    }

    @Order(1)
    @Test
    public void retrieveEbayItemsByKeyword_WhenGivenKeyword_ShouldRespondWithEbayItems()
            throws Exception {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("keyword", testKeyword);
        requestParams.put("sort", null);
        requestParams.put("filters", null);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(get(String.format("/retrieve/secured"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(requestParams)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[1].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[2].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[3].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[4].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[5].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[6].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[7].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[8].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[9].itemId").isNotEmpty());
    }

    @Order(2)
    @Test
    public void retrieveEbayItemsByKeyword_WhenGivenKeywordAndSort_ShouldRespondWithEbayItems()
            throws Exception {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("keyword", testKeyword);
        requestParams.put("sort", testSort);
        requestParams.put("filters", null);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(get("/retrieve/secured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(requestParams)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[1].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[2].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[3].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[4].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[5].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[6].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[7].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[8].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[9].itemId").isNotEmpty());
    }

    @Order(3)
    @Test
    public void retrieveEbayItemsByKeyword_WhenGivenKeywordAndFilters_ShouldRespondWithEbayItems()
            throws Exception {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("keyword", testKeyword);
        requestParams.put("sort", null);
        requestParams.put("filters", filtersList);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(get("/retrieve/secured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(requestParams)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[1].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[2].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[3].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[4].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[5].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[6].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[7].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[8].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[9].itemId").isNotEmpty());
    }

    @Order(4)
    @Test
    public void
    retrieveEbayItemsByKeyword_WhenGivenKeywordAndSortAndFilters_ShouldRespondWithEbayItems()
            throws Exception {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("keyword", testKeyword);
        requestParams.put("sort", testSort);
        requestParams.put("filters", filtersList);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(get("/retrieve/secured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(requestParams)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[1].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[2].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[3].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[4].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[5].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[6].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[7].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[8].itemId").isNotEmpty())
                .andExpect(jsonPath("$.[9].itemId").isNotEmpty());
    }

// To be implemented
//    @Test
//    public void
//    retrieveEbayItemByEpid_WhenGivenEpid_ShouldRespondWithTheEbayItemWhichHasTheSameId()
//            throws Exception {
//        long epid = null;
//
//        mvc.perform(get("/retrieve/secured/" + epid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + accessToken))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//    }
}
