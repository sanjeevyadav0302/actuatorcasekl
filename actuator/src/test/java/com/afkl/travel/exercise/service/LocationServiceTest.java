package com.afkl.travel.exercise.service;

import com.afkl.travel.exercise.exception.LocationNotFoundException;
import com.afkl.travel.exercise.model.LocationType;
import com.afkl.travel.exercise.model.Locations;
import com.afkl.travel.exercise.repository.LocationRepository;
import com.afkl.travel.exercise.repository.TranslationRepository;
import com.afkl.travel.exercise.util.LocationHelperUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class LocationServiceTest {

    @InjectMocks
    private LocationService locationService;
    @Mock
    private LocationRepository locationRepository;

    @Mock
    private TranslationRepository translationRepository;

    private LocationHelperUtil locationHelperUtil = new LocationHelperUtil();

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getLocations_ShouldSuccessFull() {
        when(locationRepository.findAll())
                .thenReturn(locationHelperUtil.getAllLocation());

        when(translationRepository.findByLanguageAndLocationByLocation(anyString(), any()))
                .thenReturn(locationHelperUtil.getTransalation());

        List<Locations> locationList = locationService.getLocations("EN");
        assertNotNull(locationList);
        assertEquals("AMS", locationList.get(0).getCode());
        assertEquals(LocationType.CITY, locationList.get(0).getType().get());
        assertEquals(123.0, locationList.get(0).getLatitude(), 1);
        assertEquals(125.0, locationList.get(0).getLongitude(), 1);
        assertEquals("AMS", locationList.get(0).getParentCode());
        assertEquals(LocationType.COUNTRY, locationList.get(0).getParentType().get());
        assertEquals("AMS", locationList.get(1).getCode());
        assertEquals(LocationType.COUNTRY, locationList.get(1).getType().get());
        assertEquals(123.0, locationList.get(1).getLatitude(), 1);
        assertEquals(125.0, locationList.get(1).getLongitude(), 1);
    }

    @Test
    public void getLocationByTypeAndCode_ShouldSuccessFull_ForAValidTypeAndCode() {
        when(locationRepository.findByTypeAndCode(anyString(), any()))
                .thenReturn(locationHelperUtil.getOptionalLocation());

        when(translationRepository.findByLanguageAndLocationByLocation(anyString(), any()))
                .thenReturn(locationHelperUtil.getTransalation());

        Locations locations = locationService.getLocationByTypeAndCode("country", "IN", "EN");
        assertNotNull(locations);
        assertEquals("AMS", locations.getCode());
        assertEquals(LocationType.CITY, locations.getType().get());
        assertEquals(123.0, locations.getLatitude(), 1);
        assertEquals(125.0, locations.getLongitude(), 1);
    }

    @Test(expected = LocationNotFoundException.class)
    public void getLocationByTypeAndCode_ShouldFail_ForInvalidTypeAndCode() {
        when(locationRepository.findByTypeAndCode(anyString(), any()))
                .thenThrow(new LocationNotFoundException("Location not found"));
        Locations locations = locationService.getLocationByTypeAndCode("country", "IN", "EN");
    }


    @Test(expected = LocationNotFoundException.class)
    public void getAirportsByCountryName_ShouldFail_ForInvalidCountry() {
        when(locationRepository.findByTypeAndCode(anyString(), any()))
                .thenThrow(new LocationNotFoundException("Location not found"));
        List<Locations> locations = locationService.getAirportsByCountryName("IN");
    }

    @Test
    public void getAirportsByCountryName_ShouldSuccessful_ForValidCountry() {
        when(locationRepository.findByTypeAndCode(anyString(), any()))
                .thenReturn(locationHelperUtil.getOptionalAirportLocation());

        when(locationRepository.findByLocationByParentId(any()))
                .thenReturn(locationHelperUtil.getAllAirportLocation());
        List<Locations> locations = locationService.getAirportsByCountryName("IN");
        assertNotNull(locations);
        assertEquals("AMS", locations.get(0).getCode());
        assertEquals(LocationType.AIRPORT, locations.get(0).getType().get());
        assertEquals(123.0, locations.get(0).getLatitude(), 1);
        assertEquals(125.0, locations.get(0).getLongitude(), 1);
    }

}
