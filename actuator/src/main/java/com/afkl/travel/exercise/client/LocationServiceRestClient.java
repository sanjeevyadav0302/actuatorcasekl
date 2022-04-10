package com.afkl.travel.exercise.client;

import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.exception.LocationServiceNotFoundException;
import com.afkl.travel.exercise.model.Locations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest client that will get airport details for country code US while application start
 */
@Component
public class LocationServiceRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceRestClient.class);

    @Value("${endpoint.travel.host}")
    private String host;

    @Value("${endpoint.travel.port}")
    private String port;

    @Value("${endpoint.travel}")
    private String travel;

    @Value("${endpoint.travel.location}")
    private String location;

    @Value("${spring.security.user.name}")
    private String userName;

    @Value("${spring.security.user.password}")
    private String password;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * get all airport location  for a country
     *
     * @param countryName
     * @return Locations
     */
    public List<Locations> getAirportsLocation(String countryName) {
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<List<Locations>> response;
        List<Locations> responseBody = new ArrayList<>();
        String uri = host + port + travel + location + "/" + countryName;
        LOGGER.debug("Constructed uri is :" + uri);
        try {
            response = restTemplate.exchange(uri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Locations>>() {
                    });
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                responseBody = response.getBody();
            }
            if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new LocationNotFoundException("airport not found for the given country");
            }
        } catch (Exception e) {
            LOGGER.error("request id in getAirportsLocation with country : " + countryName + " end up with exception :" + e.getMessage());
            throw new LocationServiceNotFoundException(" Exception occurred while fetching airports");
        }
        return responseBody;
    }

    /**
     * Create Header with basic authentication using user name and password
     * @return header
     */
    public HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("someuser", "psw");
        return headers;
    }
}
