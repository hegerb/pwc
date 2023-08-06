package com.pwc.route.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwc.route.data.Country;
import com.pwc.route.service.model.Route;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Branislav Heger
 */
@Slf4j
@Service
public class RoutingServiceImpl implements RoutingService {

    Graph<String, DefaultEdge> countryDataGraph;

    @PostConstruct
    private void init() {

        InputStream inJson = Country[].class.getResourceAsStream("/routeDate.json");
        try {
            List<Country> countryData = Arrays.asList(new ObjectMapper().readValue(inJson, Country[].class));

            countryDataGraph = new SimpleGraph<>(DefaultEdge.class);
            countryData.forEach(country ->
                    countryDataGraph.addVertex(country.getCca3())
            );
            countryData.forEach(country ->

                    country.getBorders().forEach(border ->
                            countryDataGraph.addEdge(country.getCca3(), border)
                    )
            );
        } catch (IOException e) {
            log.error("Could not read county data from file");
        }
    }

    @Override
    public Route calculateRoute(String source, String target) {

        @SuppressWarnings("rawtypes") DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(countryDataGraph);
        List<String> shortestPath = null;
        try {
            shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        } catch (NullPointerException npe) {
            log.error(npe.getMessage());
            log.debug("Path was not found, returning empty list");
            shortestPath = Collections.emptyList();
        } finally {
            return new Route(shortestPath);
        }
    }
}
