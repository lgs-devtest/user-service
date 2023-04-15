package com.devtest.user.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.devtest.user.model.GeoLocationData;

@Service
public class GeoLocationService {

  private static final String GEO_LOCATION_SERVICE_URL = "http://ip-api.com/json/";

  private RestTemplate restTemplate;

  public GeoLocationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public GeoLocationData getGeoLocation(final String ipAddress) {

    Assert.hasLength(ipAddress, "ipAddress can not be null");

    return restTemplate.getForObject(GEO_LOCATION_SERVICE_URL + ipAddress, GeoLocationData.class);
  }
}
