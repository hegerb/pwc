package com.pwc.route.mapper;

import com.pwc.route.openapi.model.RouteResponse;
import com.pwc.route.service.model.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Branislav Heger
 */
@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(target = "route", source = "route.shortestPath")
    RouteResponse map(Route route);
}
