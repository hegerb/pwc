package com.pwc.route.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Branislav Heger
 */
@Data
@RequiredArgsConstructor
public class Route {

    private final List<String> shortestPath;

}
