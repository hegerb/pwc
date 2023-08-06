package com.pwc.route.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Branislav Heger
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private String cca3;
    private List<String> borders = new ArrayList<>();
}
