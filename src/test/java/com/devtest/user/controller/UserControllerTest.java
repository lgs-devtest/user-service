package com.devtest.user.controller;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.devtest.user.dto.UserDto;
import com.devtest.user.model.GeoLocationData;
import com.devtest.user.service.GeoLocationService;
import com.devtest.user.service.UserEligibleValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @MockBean
  private GeoLocationService geoLocationService;

  @MockBean
  private UserEligibleValidationService userEligibleValidationService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void testRegisterUserAPI_withMissingUsername_ValidationFailed() throws Exception {

    // Inout
    UserDto userDto = new UserDto();
    userDto.setUsername("");
    userDto.setPassword("Abcde#123");
    userDto.setIpAddress("12.23.79.68");

    String expectedMessage = "username must not be blank";

    // Invoke
    mvc.perform( MockMvcRequestBuilders
        .post("/users")
        .content(asJsonString(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value(expectedMessage));
  }

  @Test
  public void testRegisterUserAPI_withWrongPassword_ValidationFailed() throws Exception {

    // Inout
    UserDto userDto = new UserDto();
    userDto.setUsername("abd.def");
    userDto.setPassword("Abe#23");
    userDto.setIpAddress("12.23.79.68");

    String expectedMessage = "Password need to be greater than 8 characters, containing at least 1 number, 1 Captialized letter, 1 special character in this set (_ # $ % .)";

    // Invoke
    mvc.perform( MockMvcRequestBuilders
        .post("/users")
        .content(asJsonString(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value(expectedMessage));
  }

  @Test
  public void testRegisterUserAPI_withInvalidIP_ValidationFailed() throws Exception {

    // Inout
    UserDto userDto = new UserDto();
    userDto.setUsername("abc.long");
    userDto.setPassword("Abe#1234");
    userDto.setIpAddress("abc.23.79.68");

    String expectedMessage = "ipAddress is not valid";
    // Invoke
    mvc.perform( MockMvcRequestBuilders
        .post("/users")
        .content(asJsonString(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value(expectedMessage));
  }

  @Test
  public void testRegisterUserAPI_withNotCanadaIp_NotEligible() throws Exception {

    // Stub
    String stubbedNotCanadaIp = "12.23.79.68";
    GeoLocationData stubbedLocationData = new GeoLocationData();
    stubbedLocationData.setCity("San Jose");
    stubbedLocationData.setCountry("United States");
    stubbedLocationData.setCountryCode("US");
    stubbedLocationData.setRegion("CA");
    stubbedLocationData.setRegionName("California");
    stubbedLocationData.setZip("95112");

    // Mock
    when(geoLocationService.getGeoLocation(stubbedNotCanadaIp)).thenReturn(stubbedLocationData);
    when(userEligibleValidationService.isUserEligible(stubbedLocationData)).thenReturn(false);

    UserDto userDto = new UserDto();
    userDto.setUsername("abc.long");
    userDto.setPassword("Abcde#123");
    userDto.setIpAddress(stubbedNotCanadaIp);

    String expectedMessage = "User is not eligible to register";

    // Invoke
    mvc.perform( MockMvcRequestBuilders
        .post("/users")
        .content(asJsonString(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(containsString(expectedMessage)));
  }

  @Test
  public void testRegisterUserAPI_canadaIp_Succeed() throws Exception {

    // Stub
    String stubbedIpAddress = "144.172.146.30";

    GeoLocationData stubbedLocationData = new GeoLocationData();
    stubbedLocationData.setCity("Brossard");
    stubbedLocationData.setCountry("Canada");
    stubbedLocationData.setCountryCode("CA");
    stubbedLocationData.setRegion("QC");
    stubbedLocationData.setRegionName("Quebec");
    stubbedLocationData.setZip("J4Y");

    when(geoLocationService.getGeoLocation(stubbedIpAddress)).thenReturn(stubbedLocationData);
    when(userEligibleValidationService.isUserEligible(stubbedLocationData)).thenReturn(true);

    UserDto userDto = new UserDto();
    userDto.setUsername("alex.tran");
    userDto.setPassword("Abcde#123");
    userDto.setIpAddress(stubbedIpAddress);

    String expectedMessage = "Hi alex.tran, Welcome to become a new member";
    String expectedUsername = "alex.tran";
    String expectedCity = "Brossard";

    mvc.perform( MockMvcRequestBuilders
        .post("/users")
        .content(asJsonString(userDto))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedMessage))
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(expectedUsername))
        .andExpect(MockMvcResultMatchers.jsonPath("$.city").value(expectedCity));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
 
}