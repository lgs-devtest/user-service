package com.devtest.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.devtest.user.model.GeoLocationData;

@SpringBootTest
public class GeoLocationServiceTest {

  @MockBean
  private RestTemplate restTemplateMock;

  @Value("${service.geoLocation.url}")
  private String geoLocationServiceUrl;

  @Autowired
  private GeoLocationService geoLocationService;


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

    when(restTemplateMock.getForObject(geoLocationServiceUrl + notCanadaIp, GeoLocationData.class))
        .thenReturn(stubbedLocationData);

    // Invoke
    GeoLocationData resultLocationData = geoLocationService.getGeoLocation(notCanadaIp);

    // Verify
    verify(restTemplateMock, times(1)).getForObject(geoLocationServiceUrl + notCanadaIp, GeoLocationData.class);

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

    when(restTemplateMock.getForObject(geoLocationServiceUrl + caIpAddress, GeoLocationData.class))
        .thenReturn(stubbedLocationData);

    // Invoke
    GeoLocationData resultLocationData = geoLocationService.getGeoLocation(caIpAddress);

    // Verify
    verify(restTemplateMock, times(1)).getForObject(geoLocationServiceUrl + caIpAddress, GeoLocationData.class);

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
