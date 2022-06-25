package com.github.alexeyzausalin.reservation.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeyzausalin.reservation.api.data.FacilityTestDataFactory;
import com.github.alexeyzausalin.reservation.domain.dto.FacilityView;
import com.github.alexeyzausalin.reservation.domain.dto.ListResponse;
import com.github.alexeyzausalin.reservation.domain.dto.SearchFacilitiesQuery;
import com.github.alexeyzausalin.reservation.domain.dto.SearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.alexeyzausalin.reservation.util.JsonHelper.fromJson;
import static com.github.alexeyzausalin.reservation.util.JsonHelper.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FacilitySearchApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FacilityTestDataFactory facilityTestDataFactory;

    @Test
    public void testSearch() throws Exception {
        FacilityView facilityView1 = facilityTestDataFactory.createFacility("Facility Search A group", "Facility Search A name");
        FacilityView facilityView2 = facilityTestDataFactory.createFacility("Facility Search B group", "Facility Search B name");
        FacilityView facilityView3 = facilityTestDataFactory.createFacility("Facility Search C group", "Facility Search C name");
        FacilityView facilityView4 = facilityTestDataFactory.createFacility("Facility Search D group", "Facility Search D name");
        FacilityView facilityView5 = facilityTestDataFactory.createFacility("Facility Search E group", "Facility Search E name");
        FacilityView facilityView6 = facilityTestDataFactory.createFacility("Facility Search F group", "Facility Search F name");
        FacilityView facilityView7 = facilityTestDataFactory.createFacility("Facility Search G group", "Facility Search G name");
        FacilityView facilityView8 = facilityTestDataFactory.createFacility("Facility Search H group", "Facility Search H name");
        FacilityView facilityView9 = facilityTestDataFactory.createFacility("Facility Search I group", "Facility Search I name");
        FacilityView facilityView10 = facilityTestDataFactory.createFacility("Facility Search J group", "Facility Search J name");

        testIdFilter(facilityView1.id());
        testNameFilter();
        testGroupFilter(facilityView1.group());

        facilityTestDataFactory.deleteFacility(facilityView1.id());
        facilityTestDataFactory.deleteFacility(facilityView2.id());
        facilityTestDataFactory.deleteFacility(facilityView3.id());
        facilityTestDataFactory.deleteFacility(facilityView4.id());
        facilityTestDataFactory.deleteFacility(facilityView5.id());
        facilityTestDataFactory.deleteFacility(facilityView6.id());
        facilityTestDataFactory.deleteFacility(facilityView7.id());
        facilityTestDataFactory.deleteFacility(facilityView8.id());
        facilityTestDataFactory.deleteFacility(facilityView9.id());
        facilityTestDataFactory.deleteFacility(facilityView10.id());
    }

    private void testIdFilter(String id) throws Exception {
        SearchFacilitiesQuery query;
        ListResponse<FacilityView> facilityViewList;

        // Search query with facility id equal
        query = SearchFacilitiesQuery.builder().id(id).build();
        facilityViewList = execute("/api/v1/facilities/search", query);
        assertEquals(1, facilityViewList.items().size(), "Invalid search result!");
    }

    private void testNameFilter() throws Exception {
        SearchFacilitiesQuery query;
        ListResponse<FacilityView> facilityViewList;

        // Search query facility title contains
        query = SearchFacilitiesQuery.builder().name("Facility Search G").build();
        facilityViewList = execute("/api/v1/facilities/search", query);
        assertEquals(1, facilityViewList.items().size(), "Invalid search result!");

        // Search query facility name contains case-insensitive
        query = SearchFacilitiesQuery.builder().name("Facility Search g").build();
        facilityViewList = execute("/api/v1/facilities/search", query);
        assertEquals(1, facilityViewList.items().size(), "Invalid search result!");
    }

    private void testGroupFilter(String group) throws Exception {
        SearchFacilitiesQuery query;
        ListResponse<FacilityView> facilityViewList;

        // Search query with facility group equal
        query = SearchFacilitiesQuery.builder().group(group).build();
        facilityViewList = execute("/api/v1/facilities/search", query);
        assertEquals(1, facilityViewList.items().size(), "Invalid search result!");
    }

    private ListResponse<FacilityView> execute(String url, SearchFacilitiesQuery query) throws Exception {
        MvcResult result = this.mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, new SearchRequest<>(query))))
                .andExpect(status().isOk())
                .andReturn();

        return fromJson(objectMapper,
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
    }
}
