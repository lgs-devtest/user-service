package com.devtest.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.devtest.user.model.GeoLocationData;

@Service
public class UserEligibleValidationService {

  @Value("${user.eligible.countryCode}")
  private String eligibleCountryCode;

  public boolean isUserEligible(final GeoLocationData geoLocationData) {

    if (StringUtils.hasLength(eligibleCountryCode)
        && eligibleCountryCode.equalsIgnoreCase(geoLocationData.getCountryCode())) {
      return true;
    }
    return false;
  }

}
