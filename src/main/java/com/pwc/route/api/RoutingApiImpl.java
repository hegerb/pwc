package com.pwc.route.api;

import com.pwc.route.mapper.RouteMapper;
import com.pwc.route.openapi.api.RoutingApi;
import com.pwc.route.openapi.model.RouteResponse;
import com.pwc.route.service.RoutingService;
import com.pwc.route.service.model.Route;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Branislav Heger
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoutingApiImpl implements RoutingApi {

    private final RoutingService routingService;
    private final RouteMapper routeMapper;

    @Override
    public ResponseEntity<RouteResponse> route(String source, String target) {
        final Route route = routingService.calculateRoute(source, target);
        if (route.getShortestPath().isEmpty()) {
            log.debug("Route for source {} and target {} not found", source, target);
            return ResponseEntity.badRequest().body(null);
        } else {
            log.debug("Route for source {} and target {} found {}", source, target, route);
            return ResponseEntity.ok(routeMapper.map(route));
        }
    }
}
