package com.devtest.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.devtest.user.model.GeoLocationData;

@ExtendWith(MockitoExtension.class)
public class GeoLocationServiceTest {

  @Mock
  private RestTemplate restTemplateMock;

  @InjectMocks
  private GeoLocationService geoLocationService;

  private static final String GEO_LOCATION_SERVICE_URL = "http://ip-api.com/json/";

  @Test()
  public void testGetGeoLocation_withNullIpAddress_Exception() {

    // Input
    String ipAddress = null;

    // Exception Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      geoLocationService.getGeoLocation(ipAddress);
    });

    // Verify
    verifyNoInteractions(restTemplateMock);

    // Assert
    String expectedMessage = "ipAddress can not be null";
    assertEquals(expectedMessage, exception.getMessage());

  }

  @Test()
  public void testGetGeoLocation_withEmptyIpAddress_Exception() {

    // Input
    String ipAddress = "";

    // Exception Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      geoLocationService.getGeoLocation(ipAddress);
    });

    // Verify
    verifyNoInteractions(restTemplateMock);

    // Assert
    String expectedMessage = "ipAddress can not be null";
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  public void testGetGeoLocation_withNotCanadaIP_Succeed() {

    // Input
    String notCanadaIp = "12.23.79.68";

    // Stub
    GeoLocationData stubbedLocationData = new GeoLocationData();
    stubbedLocationData.setCity("San Jose");
    stubbedLocationData.setCountry("United States");
    stubbedLocationData.setCountryCode("US");
    stubbedLocationData.setRegion("CA");
    stubbedLocationData.setRegionName("California");
    stubbedLocationData.setZip("95112");

    when(restTemplateMock.getForObject(GEO_LOCATION_SERVICE_URL + notCanadaIp, GeoLocationData.class))
        .thenReturn(stubbedLocationData);

    // Invoke
    GeoLocationData resultLocationData = geoLocationService.getGeoLocation(notCanadaIp);

    // Verify
    verify(restTemplateMock, times(1)).getForObject(GEO_LOCATION_SERVICE_URL + notCanadaIp, GeoLocationData.class);

    // Assert
    assertNotNull(resultLocationData);
    assertEquals(stubbedLocationData.getCountry(), resultLocationData.getCountry());
    assertEquals(stubbedLocationData.getCountryCode(), resultLocationData.getCountryCode());
    assertEquals(stubbedLocationData.getCity(), resultLocationData.getCity());
    assertEquals(stubbedLocationData.getRegion(), resultLocationData.getRegion());
    assertEquals(stubbedLocationData.getRegionName(), resultLocationData.getRegionName());
    assertEquals(stubbedLocationData.getZip(), resultLocationData.getZip());
  }

  @Test
  public void testGetGeoLocation_withCanadaIP_Succeed() {

    // Input
    String caIpAddress = "144.172.146.30";

    // Stub
    GeoLocationData stubbedLocationData = new GeoLocationData();
    stubbedLocationData.setCity("Brossard");
    stubbedLocationData.setCountry("Canada");
    stubbedLocationData.setCountryCode("CA");
    stubbedLocationData.setRegion("QC");
    stubbedLocationData.setRegionName("Quebec");
    stubbedLocationData.setZip("J4Y");

    when(restTemplateMock.getForObject(GEO_LOCATION_SERVICE_URL + caIpAddress, GeoLocationData.class))
        .thenReturn(stubbedLocationData);

    // Invoke
    GeoLocationData resultLocationData = geoLocationService.getGeoLocation(caIpAddress);

    // Verify
    verify(restTemplateMock, times(1)).getForObject(GEO_LOCATION_SERVICE_URL + caIpAddress, GeoLocationData.class);

    // Assert
    assertNotNull(resultLocationData);
    assertEquals(stubbedLocationData.getCountry(), resultLocationData.getCountry());
    assertEquals(stubbedLocationData.getCountryCode(), resultLocationData.getCountryCode());
    assertEquals(stubbedLocationData.getCity(), resultLocationData.getCity());
    assertEquals(stubbedLocationData.getRegion(), resultLocationData.getRegion());
    assertEquals(stubbedLocationData.getRegionName(), resultLocationData.getRegionName());
    assertEquals(stubbedLocationData.getZip(), resultLocationData.getZip());
  }

}
