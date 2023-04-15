package com.devtest.user.controller;

import javax.validation.Valid;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devtest.user.dto.RegistrationSuccessDto;
import com.devtest.user.dto.UserDto;
import com.devtest.user.exception.UserNotEligibleException;
import com.devtest.user.model.GeoLocationData;
import com.devtest.user.service.GeoLocationService;
import com.devtest.user.service.UserEligibleValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "User Registration API")
@RestController
@RequestMapping("/users")
public class UserController {

  private GeoLocationService geoLocationService;

  private UserEligibleValidationService userEligibleValidationService;

  public UserController(final GeoLocationService geoLocationService, final UserEligibleValidationService userEligibleValidationService) {
    this.geoLocationService = geoLocationService;
    this.userEligibleValidationService = userEligibleValidationService;
  }

  @ApiOperation(value = "This API is used to register a new user")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful user registration"),
      @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 500, message = "Internal Server Error")})
  @PostMapping
  public ResponseEntity<RegistrationSuccessDto> createUser(
      @ApiParam(value = "Input User Information", name = "user", required = true)
      @Valid @RequestBody UserDto userDto) {

    GeoLocationData locationData = geoLocationService.getGeoLocation(userDto.getIpAddress());

    if (!userEligibleValidationService.isUserEligible(locationData)) {
      throw new UserNotEligibleException("User is not eligible to register");
    }

    RegistrationSuccessDto registrationSuccessDto = RegistrationSuccessDto.builder()
        .uuid(UUID.randomUUID())
        .message(String.format("Hi %s, Welcome to become a new member", userDto.getUsername()))
        .username(userDto.getUsername())
        .city(locationData.getCity())
        .build();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(registrationSuccessDto);
  }
}
