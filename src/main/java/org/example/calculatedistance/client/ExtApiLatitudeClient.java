package org.example.calculatedistance.client;

import org.example.calculatedistance.dto.CityData;

public interface ExtApiLatitudeClient {
    CityData getCityData(String fromState, String fromCity);
}
