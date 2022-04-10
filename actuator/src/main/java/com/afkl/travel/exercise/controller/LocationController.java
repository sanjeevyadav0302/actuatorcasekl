package com.afkl.travel.exercise.controller;


import com.afkl.travel.exercise.model.Locations;
import com.afkl.travel.exercise.service.LocationService;
import com.afkl.travel.exercise.service.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${endpoint.travel}")
public class LocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    LocationService locationService;

    @Autowired
    MetricsService actuatorMetricsService;


    @Value("${languages}")
    private List<String> languages;


    /**
     * Get all the locations
     * @param language
     * @return
     */

    @GetMapping("${endpoint.travel.location}")
    public List<Locations> getLocations(@RequestHeader(value = "accept-language", defaultValue = "EN") String language) {
        LOGGER.info("request id for getLocations");
        return locationService.getLocations(getLanguage(language));
    }

    /**
     * Get country/city/airport location by providing code.
     * @param type
     * @param code
     * @param language
     * @return
     */
    @GetMapping("${endpoints.travel.location-by-type-code}")
    public Locations getLocationByTypeAndCode(@PathVariable("type") String type,
                                              @PathVariable("code") String code,
                                              @RequestHeader(value = "accept-language", defaultValue = "EN") String language) {
        LOGGER.info("request for getLocationByTypeAndCode");
        return locationService.getLocationByTypeAndCode(type, code, getLanguage(language));
    }

    /**
     * Get all the airport for the country
     * @param countryCode
     * @return
     */
    @GetMapping("${endpoints.travel.airport-by-type}")
    public List<Locations> getAirportsByCountryName(@PathVariable("countryCode") String countryCode) {
        LOGGER.info("request id for getAirportsByCountryName");
        return locationService.getAirportsByCountryName(countryCode);
    }

    /**
     * Check for language,if other than nl/en,then go for default en
     * @param language
     * @return
     */
    private String getLanguage(String language) {
        if (!languages.contains(language)) {
            language = "EN";
        }
        return language;
    }

}
