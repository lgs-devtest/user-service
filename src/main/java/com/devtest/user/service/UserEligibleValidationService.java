package com.devtest.user.service;

import org.springframework.stereotype.Service;

import com.devtest.user.model.GeoLocationData;

@Service
public class UserEligibleValidationService {

  private static final String ELIGIBLE_COUNTRY = "Canada";

  public boolean isUserEligible(final GeoLocationData geoLocationData) {

    if (ELIGIBLE_COUNTRY.equalsIgnoreCase(geoLocationData.getCountry())) {
      return true;
    }
    return false;
  }

}
