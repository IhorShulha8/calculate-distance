package org.example.calculatedistance.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.example.calculatedistance.dto.CityData;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ExtApiLatitudeClientImpl implements ExtApiLatitudeClient {

    @Value("${api.external.url}")
    private String externalUrl;

    private final RestClient restClient;

    @Override
    public CityData getCityData(final String state, final String city) {

        return restClient
                .get()
                .uri(externalUrl, state, city)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new IOException(String.format("Error thrown while external call. HttpStatus: '%d'", response.getStatusCode().value()));
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new IOException(String.format("Server error. HttpStatus: '%d'", response.getStatusCode().value()));
                }))
                .toEntity(CityData.class)
                .getBody();
    }
}
