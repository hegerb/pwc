package com.pwc.route.service;

import com.pwc.route.service.model.Route;

/**
 * @author Branislav Heger
 */
public interface RoutingService {


    /**
     * Calculates route between two countries
     *
     * @param source source country
     * @param target target country
     * @return list of countries representing the route, empty list if the rout does not exists
     */
    Route calculateRoute(String source, String target);
}
