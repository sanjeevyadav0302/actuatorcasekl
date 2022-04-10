package com.afkl.travel.exercise.client;

import com.afkl.travel.exercise.exception.LocationServiceNotFoundException;
import com.afkl.travel.exercise.model.LocationType;
import com.afkl.travel.exercise.model.Locations;
import com.afkl.travel.exercise.util.LocationHelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
public class LocationServiceRestClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LocationServiceRestClient locationServiceRestClient;

    private LocationHelperUtil locationHelperUtil = new LocationHelperUtil();

    @Test
    public void getAirports_ShouldSuccessful_ForValidCountry() {
        List<Locations> locations = locationHelperUtil.getLocations();

        HttpEntity<String> entity = new HttpEntity<String>(getDummyHttpHeaders());
        ResponseEntity<List<Locations>> myEntity = new ResponseEntity<List<Locations>>(locations, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Locations>>>any())
        ).thenReturn(myEntity);

        List<Locations> airportList = locationServiceRestClient.getAirportsLocation("US");
        assertNotNull(airportList);
        assertEquals("AMS", airportList.get(0).getCode());
        assertEquals(LocationType.AIRPORT, airportList.get(0).getType().get());

    }

    @Test(expected = LocationServiceNotFoundException.class)
    public void getAirports_ShouldServiceNotFound_ForServiceNotUp() {
        List<Locations> locations = locationHelperUtil.getLocations();
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Locations>>>any())
        ).thenThrow(new LocationServiceNotFoundException("Exception occurred while fetching airports"));
        List<Locations> airportList = locationServiceRestClient.getAirportsLocation("US");
    }

    private HttpHeaders getDummyHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("as", "as");
        return httpHeaders;
    }
}
