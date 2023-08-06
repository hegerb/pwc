package com.pwc.route.service;

import com.pwc.route.service.model.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;


/**
 * @author Branislav Heger
 */
public class RoutingServiceTest {


    private RoutingService routingService = new RoutingServiceImpl();

    @BeforeEach
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method postConstruct = RoutingServiceImpl.class.getDeclaredMethod("init", null);
        postConstruct.setAccessible(true);
        postConstruct.invoke(routingService);
    }


    @Test
    public void calculateRoute_shouldFindRoute() {
        assertEquals(new Route(Arrays.asList("SVK", "AUT", "DEU")), routingService.calculateRoute("SVK", "DEU"));
    }


    @Test
    public void calculateRoute_shouldFindLongRoute() {
        assertEquals(new Route(Arrays.asList("IND", "CHN", "RUS", "POL", "DEU", "FRA", "ESP", "PRT")), routingService.calculateRoute("IND", "PRT"));
    }

    @Test
    public void calculateRoute_shouldNotFindRoute() {
        assertEquals(new Route(Collections.emptyList()), routingService.calculateRoute("IND", "USA"));
    }


}
