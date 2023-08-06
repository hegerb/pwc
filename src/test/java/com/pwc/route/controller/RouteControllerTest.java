package com.pwc.route.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pwc.route.api.RoutingApiImpl;
import com.pwc.route.mapper.RouteMapper;
import com.pwc.route.openapi.model.RouteResponse;
import com.pwc.route.service.RoutingService;
import com.pwc.route.service.model.Route;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Branislav Heger
 */
@WebMvcTest(RoutingApiImpl.class)
@ComponentScan(basePackageClasses = RouteMapper.class)
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoutingService service;
    @Autowired
    private RouteMapper routeMapper;

    @Test
    public void shouldReturn_Route() throws Exception {
        final List<String> route = Arrays.asList("IND", "CHN", "RUS", "POL", "DEU", "FRA", "ESP", "PRT");
        final Route resultRoute = new Route(route);

        final JSONObject jsonObject = createExpectedResponse(route);

        when(service.calculateRoute(any(), any())).thenReturn(resultRoute);

        this.mockMvc.perform(get("/routing/IND/PRT")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonObject.toString()));
    }

    @Test
    public void shouldReturn_notFound() throws Exception {
        this.mockMvc.perform(get("/routingWrongPath/IND/PRT")).andDo(print()).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    private JSONObject createExpectedResponse(List<String> route) throws JSONException, JsonProcessingException {
        final RouteResponse routeResponse = new RouteResponse();
        routeResponse.setRoute(route);

        return getJsonObject(routeResponse);
    }

    private JSONObject getJsonObject(RouteResponse routeResponse) throws JSONException, JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return new JSONObject(ow.writeValueAsString(routeResponse));
    }
}