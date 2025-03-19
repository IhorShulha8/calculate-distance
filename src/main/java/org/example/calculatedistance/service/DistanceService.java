package org.example.calculatedistance.service;


public interface DistanceService {

    Double getDistance(String fromPostCode, String fromState, String fromCity,
                       String toPostCode, String toState, String toCity);
}
