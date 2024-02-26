package org.tes.productretrieverservice.unit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tes.productretrieverservice.controller.ProductRetrieverController;
import org.tes.productretrieverservice.model.EbayItemEntity;
import org.tes.productretrieverservice.service.ProductRetrieverService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProductRetrieverControllerTests {

    @Mock
    ProductRetrieverService service;

    @InjectMocks
    ProductRetrieverController controller;

    List<String> filters = new ArrayList<>();

    @BeforeAll
    public void setUpFilters() {
        filters.add("conditions:{NEW|USED}");
    }

    @Test
    public void retrieveEbayItemsByKeyword_WhenGivenKeyword_ShouldRespondWithEbayItems() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("keyword", "car");
        requestMap.put("sort", null);
        requestMap.put("filters", null);

        EbayItemEntity ebayItem = new EbayItemEntity();
        ArrayList<EbayItemEntity> ebayItemsList = new ArrayList<>();
        ebayItemsList.add(ebayItem);

        when(service.retrieveEbayItemsByKeyword(requestMap)).thenReturn(ebayItemsList);

        assertThat(controller.retrieveItemsByKeyword(requestMap)).isEqualTo(ebayItemsList);
    }

    @Test
    public void retrieveEbayItemsByKeyword_WhenGivenKeywordAndSort_ShouldRespondWithEbayItems() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("keyword", "car");
        requestMap.put("sort", "-price");
        requestMap.put("filters", null);

        EbayItemEntity ebayItem = new EbayItemEntity();
        ArrayList<EbayItemEntity> ebayItemsList = new ArrayList<>();
        ebayItemsList.add(ebayItem);

        when(service.retrieveEbayItemsByKeyword(requestMap)).thenReturn(ebayItemsList);

        assertThat(controller.retrieveItemsByKeyword(requestMap)).isEqualTo(ebayItemsList);
    }

    @Test
    public void retrieveEbayItemsByKeyword_WhenGivenKeywordAndFilters_ShouldRespondWithEbayItems() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("keyword", "car");
        requestMap.put("sort", null);
        requestMap.put("filters", filters);

        EbayItemEntity ebayItem = new EbayItemEntity();
        ArrayList<EbayItemEntity> ebayItemsList = new ArrayList<>();
        ebayItemsList.add(ebayItem);

        when(service.retrieveEbayItemsByKeyword(requestMap)).thenReturn(ebayItemsList);

        assertThat(controller.retrieveItemsByKeyword(requestMap)).isEqualTo(ebayItemsList);
    }

    @Test
    public void retrieveEbayItemsByKeyword_WhenGivenKeywordAndSortAndFilters_ShouldRespondWithEbayItems() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("keyword", "car");
        requestMap.put("sort", "-price");
        requestMap.put("filters", filters);

        EbayItemEntity ebayItem = new EbayItemEntity();
        ArrayList<EbayItemEntity> ebayItemsList = new ArrayList<>();
        ebayItemsList.add(ebayItem);

        when(service.retrieveEbayItemsByKeyword(requestMap)).thenReturn(ebayItemsList);

        assertThat(controller.retrieveItemsByKeyword(requestMap)).isEqualTo(ebayItemsList);
    }
}
