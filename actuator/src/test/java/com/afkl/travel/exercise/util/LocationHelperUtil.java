package com.afkl.travel.exercise.util;

import com.afkl.travel.exercise.entity.Location;
import com.afkl.travel.exercise.entity.Translation;
import com.afkl.travel.exercise.model.LocationType;
import com.afkl.travel.exercise.model.Locations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationHelperUtil {

    public List<Locations> getLocations() {
        List<Locations> locationsList = new ArrayList<>();
        Locations location = new Locations();
        location.setName("ams");
        LocationType locationType = LocationType.AIRPORT;
        location.setType(Optional.of(locationType));
        location.setCode("AMS");
        location.setLongitude(123.00);
        location.setLatitude(1234.00);
        location.setParentCode("Country");
        LocationType locationTypeCountry = LocationType.CITY;
        location.setParentType(Optional.of(locationTypeCountry));
        locationsList.add(location);
        return locationsList;
    }

    public Optional<Translation> getTransalation() {
        Location location = new Location();
        location.setId(2);
        location.setCode("AMS");
        location.setType("country");
        location.setLatitude(123.00);
        Translation translation = new Translation();
        translation.setId(2);
        translation.setDescription("new ams");
        translation.setLanguage("EN");
        translation.setName("AMS");
        translation.setLocationByLocation(location);
        return Optional.of(translation);
    }

    public Optional<Location> getOptionalLocation() {
        Location location = new Location();
        location.setId(2);
        location.setCode("AMS");
        location.setType("city");
        location.setLatitude(123.00);
        location.setLongitude(125.00);
        Location location1 = new Location();
        location1.setId(3);
        location1.setCode("AMS");
        location1.setType("country");
        location1.setLatitude(123.00);
        location1.setLongitude(125.00);
        location.setLocationByParentId(location1);
        return Optional.of(location);
    }

    public Optional<Location> getOptionalAirportLocation() {
        Location location = new Location();
        location.setId(2);
        location.setCode("AMS");
        location.setType("city");
        location.setLatitude(123.00);
        location.setLongitude(125.00);
        Location location1 = new Location();
        location1.setId(3);
        location1.setCode("AMS");
        location1.setType("AIRPORT");
        location1.setLatitude(123.00);
        location1.setLongitude(125.00);
        location.setLocationByParentId(location1);
        return Optional.of(location);
    }

    public List<Location> getAllLocation() {
        List<Location> locationList = new ArrayList<>();
        Location location = new Location();
        location.setId(2);
        location.setCode("AMS");
        location.setType("city");
        location.setLatitude(123.00);
        location.setLongitude(125.00);
        Location location1 = new Location();
        location1.setId(3);
        location1.setCode("AMS");
        location1.setType("country");
        location1.setLatitude(123.00);
        location1.setLongitude(125.00);
        location.setLocationByParentId(location1);
        locationList.add(location);
        locationList.add(location1);
        return locationList;

    }
    public List<Location> getAllAirportLocation() {
        List<Location> locationList = new ArrayList<>();
        Location location = new Location();
        location.setId(2);
        location.setCode("AMS");
        location.setType("city");
        location.setLatitude(123.00);
        location.setLongitude(125.00);
        Location location1 = new Location();
        location1.setId(3);
        location1.setCode("AMS");
        location1.setType("AIRPORT");
        location1.setLatitude(123.00);
        location1.setLongitude(125.00);
        location.setLocationByParentId(location1);
        locationList.add(location);
        locationList.add(location1);
        return locationList;

    }
}
