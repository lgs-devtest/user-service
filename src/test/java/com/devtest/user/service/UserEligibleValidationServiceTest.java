package com.devtest.user.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devtest.user.model.GeoLocationData;

@SpringBootTest
public class UserEligibleValidationServiceTest {

  @Autowired
  private UserEligibleValidationService eligibleValidationService;

  @Test
  public void testGetGeoLocation_withNotCanadaIP_Succeed() {

    // Input
    GeoLocationData locationData = new GeoLocationData();
    locationData.setCountry("United States");
    locationData.setCountryCode("US");

    // Invoke
    boolean resultIsUserEligible = eligibleValidationService.isUserEligible(locationData);

    // Assert
    assertFalse(resultIsUserEligible);
  }

  @Test
  public void testGetGeoLocation_withCanadaIP_Succeed() {

    // Input
    GeoLocationData locationData = new GeoLocationData();
    locationData.setCountry("Canada");
    locationData.setCountryCode("CA");

    // Invoke
    boolean resultIsUserEligible = eligibleValidationService.isUserEligible(locationData);

    // Assert
    assertTrue(resultIsUserEligible);
  }

}
