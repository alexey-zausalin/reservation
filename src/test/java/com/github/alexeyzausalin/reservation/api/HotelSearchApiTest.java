package com.github.alexeyzausalin.reservation.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexeyzausalin.reservation.api.data.HotelTestDataFactory;
import com.github.alexeyzausalin.reservation.domain.dto.HotelView;
import com.github.alexeyzausalin.reservation.domain.dto.ListResponse;
import com.github.alexeyzausalin.reservation.domain.dto.SearchHotelsQuery;
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
public class HotelSearchApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HotelTestDataFactory hotelTestDataFactory;

    @Test
    public void testSearch() throws Exception {
        HotelView hotelView1 = hotelTestDataFactory.createHotel(
                "Hotel Search A name",
                "Hotel Search A address",
                "Hotel Search A hotel type"
        );
        HotelView hotelView2 = hotelTestDataFactory.createHotel(
                "Hotel Search B name",
                "Hotel Search B address",
                "Hotel Search B hotel type"
        );
        HotelView hotelView3 = hotelTestDataFactory.createHotel(
                "Hotel Search C name",
                "Hotel Search C address",
                "Hotel Search C hotel type"
        );
        HotelView hotelView4 = hotelTestDataFactory.createHotel(
                "Hotel Search D name",
                "Hotel Search D address",
                "Hotel Search D hotel type"
        );
        HotelView hotelView5 = hotelTestDataFactory.createHotel(
                "Hotel Search E name",
                "Hotel Search E address",
                "Hotel Search E hotel type"
        );
        HotelView hotelView6 = hotelTestDataFactory.createHotel(
                "Hotel Search F name",
                "Hotel Search F address",
                "Hotel Search F hotel type"
        );
        HotelView hotelView7 = hotelTestDataFactory.createHotel(
                "Hotel Search G name",
                "Hotel Search G address",
                "Hotel Search G hotel type"
        );
        HotelView hotelView8 = hotelTestDataFactory.createHotel(
                "Hotel Search H name",
                "Hotel Search H address",
                "Hotel Search H hotel type"
        );
        HotelView hotelView9 = hotelTestDataFactory.createHotel(
                "Hotel Search I name",
                "Hotel Search I address",
                "Hotel Search I hotel type"
        );
        HotelView hotelView10 = hotelTestDataFactory.createHotel(
                "Hotel Search J name",
                "Hotel Search J address",
                "Hotel Search J hotel type"
        );

        testIdFilter(hotelView1.id());
        testNameFilter();
        testHotelTypeFilter(hotelView1.hotelType());

        hotelTestDataFactory.deleteHotel(hotelView1.id());
        hotelTestDataFactory.deleteHotel(hotelView2.id());
        hotelTestDataFactory.deleteHotel(hotelView3.id());
        hotelTestDataFactory.deleteHotel(hotelView4.id());
        hotelTestDataFactory.deleteHotel(hotelView5.id());
        hotelTestDataFactory.deleteHotel(hotelView6.id());
        hotelTestDataFactory.deleteHotel(hotelView7.id());
        hotelTestDataFactory.deleteHotel(hotelView8.id());
        hotelTestDataFactory.deleteHotel(hotelView9.id());
        hotelTestDataFactory.deleteHotel(hotelView10.id());
    }

    private void testIdFilter(String id) throws Exception {
        SearchHotelsQuery query;
        ListResponse<HotelView> hotelViewList;

        // Search query with hotel id equal
        query = SearchHotelsQuery.builder().id(id).build();
        hotelViewList = execute("/api/v1/hotels/search", query);
        assertEquals(1, hotelViewList.items().size(), "Invalid search result!");
    }

    private void testNameFilter() throws Exception {
        SearchHotelsQuery query;
        ListResponse<HotelView> hotelViewList;

        // Search query hotel title contains
        query = SearchHotelsQuery.builder().name("Hotel Search G").build();
        hotelViewList = execute("/api/v1/hotels/search", query);
        assertEquals(1, hotelViewList.items().size(), "Invalid search result!");

        // Search query hotel name contains case-insensitive
        query = SearchHotelsQuery.builder().name("Hotel Search g").build();
        hotelViewList = execute("/api/v1/hotels/search", query);
        assertEquals(1, hotelViewList.items().size(), "Invalid search result!");
    }

    private void testHotelTypeFilter(String hotelType) throws Exception {
        SearchHotelsQuery query;
        ListResponse<HotelView> hotelViewList;

        // Search query with hotel hotelType equal
        query = SearchHotelsQuery.builder().hotelType(hotelType).build();
        hotelViewList = execute("/api/v1/hotels/search", query);
        assertEquals(1, hotelViewList.items().size(), "Invalid search result!");
    }

    private ListResponse<HotelView> execute(String url, SearchHotelsQuery query) throws Exception {
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
