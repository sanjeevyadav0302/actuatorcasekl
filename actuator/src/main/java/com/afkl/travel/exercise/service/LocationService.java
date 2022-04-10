package com.afkl.travel.exercise.service;


import com.afkl.travel.exercise.entity.Location;
import com.afkl.travel.exercise.entity.Translation;
import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.model.LocationType;
import com.afkl.travel.exercise.model.Locations;
import com.afkl.travel.exercise.repository.LocationRepository;
import com.afkl.travel.exercise.repository.TranslationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TranslationRepository translationRepository;

    /**
     * Get all the type of location based on language provided
     * @param language
     * @return
     */
    public List<Locations> getLocations(final String language) {
        LOGGER.debug("request in getLocation with language :" + language);
        List<Locations> locationsList = new ArrayList<>();
        List<Location> locationList = locationRepository.findAll();
        locationList.forEach(location -> {
            Optional<Translation> translation = translationRepository.findByLanguageAndLocationByLocation(language.toUpperCase(), location);
            Locations locations = getLocationAndTranslation(location, translation);
            locationsList.add(locations);

        });
        LOGGER.info("request successfully return location");
        return locationsList;
    }

    /**
     * Get location based on type ,code and language
     * @param type
     * @param code
     * @param language
     * @return
     */
    public Locations getLocationByTypeAndCode(final String type, final String code, final String language) {
        LOGGER.debug("request id in getLocationByTypeAndCode with type :" + type + " code:" + code + " language :" + language);
        Location location = locationRepository.findByTypeAndCode(type, code).orElseThrow(() -> new LocationNotFoundException("Location not found for given type and code"));
        Optional<Translation> translation = translationRepository.findByLanguageAndLocationByLocation(language.toUpperCase(), location);
        LOGGER.info("request id successfully return location by type and code");
        return getLocationAndTranslation(location, translation);
    }

    /**
     * Converting entity to output VO
     * @param location
     * @param translation
     * @return
     */
    private Locations getLocationAndTranslation(final Location location, final Optional<Translation> translation) {
        Locations locations = new Locations();
        locations.setCode(location.getCode());
        locations.setType(LocationType.fromType(location.getType()));
        if(!locations.getType().isPresent()){
            LOGGER.error("Enum Type not supported" + locations.getType().get());
        }
        locations.setLongitude(location.getLongitude());
        locations.setLatitude(location.getLatitude());
        if (null != location.getLocationByParentId()) {
            locations.setParentCode(location.getLocationByParentId().getCode());
            locations.setParentType(LocationType.fromType(location.getLocationByParentId().getType()));
        }
        if (translation.isPresent()) {
            locations.setDescription(translation.get().getDescription());
            locations.setName(translation.get().getName());
        }
        return locations;
    }


    /**
     * Get airport details by country name
     * @param code
     * @return
     */
    public List<Locations> getAirportsByCountryName(final String code) {
        LOGGER.debug("request id in getAirportsByCountryName with code :" + code);
        List<Locations> airportList = new ArrayList<>();
        Location location = locationRepository.findByTypeAndCode("country", code).orElseThrow(() -> new LocationNotFoundException("Airport not found for given country code"));
        List<Location> listOfCity = locationRepository.findByLocationByParentId(location);
        listOfCity.forEach(city -> {
            Locations locations = new Locations();
            List<Location> listOfAirportLocation = locationRepository.findByLocationByParentId(city);
            listOfAirportLocation.forEach(airport -> {
                locations.setType(LocationType.fromType(airport.getType()));
                locations.setCode(airport.getCode());
                locations.setLatitude(airport.getLatitude());
                locations.setLongitude(airport.getLongitude());
            });
            airportList.add(locations);
        });
        LOGGER.info("request id successfully return airports name");
        return airportList;
    }
}
