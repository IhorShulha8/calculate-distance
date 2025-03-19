package org.example.calculatedistance.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityData {
    @JsonAlias({"country abbreviation"})
    private String countryAbbreviation;
    private List<Place> places;
    private String country;
    @JsonAlias({"place name"})
    private String placeName;
    private String state;
    private String abbreviation;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Place {
        @JsonAlias({"place name"})
        private String placeName;
        private String longitude;
        @JsonAlias({"post code"})
        private String postCode;
        private String latitude;

    }
}
