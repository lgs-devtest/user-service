package com.devtest.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoLocationData {

  private String country;
  private String countryCode;
  private String region;
  private String regionName;
  private String city;
  private String zip;

}
