package com.devtest.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.devtest.user.model.GeoLocationData;

@Service
public class GeoLocationService {

  @Value("${service.geoLocation.url}")
  private String geoLocationServiceUrl;

  private RestTemplate restTemplate;

  public GeoLocationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public GeoLocationData getGeoLocation(final String ipAddress) {

    Assert.hasLength(ipAddress, "ipAddress can not be null");

    return restTemplate.getForObject(geoLocationServiceUrl + ipAddress, GeoLocationData.class);
  }
}
