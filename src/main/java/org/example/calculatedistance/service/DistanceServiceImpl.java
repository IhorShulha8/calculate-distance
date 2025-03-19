package org.example.calculatedistance.service;

import lombok.RequiredArgsConstructor;
import org.example.calculatedistance.client.ExtApiLatitudeClient;
import org.example.calculatedistance.dto.CityData;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class DistanceServiceImpl implements DistanceService {

    private static final double EARTH_RADIUS = 6378.137;

    private final ExtApiLatitudeClient extApiLatitudeClient;

    public Double getDistance(final String fromPostCode, final String fromState, final String fromCity,
                              final String toPostCode, final String toState, final String toCity) {
        final CityData firstCity = extApiLatitudeClient.getCityData(fromState, fromCity);
        final CityData secondCity = extApiLatitudeClient.getCityData(toState, toCity);
        return calculateDistance(fromPostCode, firstCity, toPostCode, secondCity);
    }

    private Double calculateDistance(final String fromPostCode, final CityData firstCity,
                                     final String toPostCode, final CityData secondCity) {

        final CityData.Place firstPlace = firstCity.getPlaces().stream()
                .filter(p -> p.getPostCode().equals(fromPostCode))
                .findFirst()
                .orElseThrow();

        final CityData.Place secondPlace = secondCity.getPlaces().stream()
                .filter(p -> p.getPostCode().equals(toPostCode))
                .findFirst()
                .orElseThrow();

        double endLat = Double.parseDouble(secondPlace.getLatitude());
        double startLat = Double.parseDouble(firstPlace.getLatitude());
        final double endLong = Double.parseDouble(secondPlace.getLongitude());
        final double startLong = Double.parseDouble(firstPlace.getLongitude());

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return BigDecimal.valueOf(EARTH_RADIUS * c).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
