package org.example.calculatedistance.api;

import lombok.RequiredArgsConstructor;
import org.example.calculatedistance.service.DistanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/distances")
public class DistanceController {

    private final DistanceService distanceService;

    @GetMapping("/{fromPostCode}/{fromState}/{fromCity}/{toState}/{toPostCode}/{toCity}")
    public ResponseEntity<Double> getDistance(@PathVariable("fromPostCode") String fromPostCode,
                                              @PathVariable("fromState") String fromState,
                                              @PathVariable("fromCity") String fromCity,
                                              @PathVariable("toState") String toState,
                                              @PathVariable("toPostCode") String toPostCode,
                                              @PathVariable("toCity") String toCity) {
        return ResponseEntity.ok(distanceService.getDistance(fromPostCode, fromState, fromCity, toPostCode, toState, toCity));
    }
}
