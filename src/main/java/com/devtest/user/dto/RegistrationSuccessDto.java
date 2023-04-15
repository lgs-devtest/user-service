package com.devtest.user.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationSuccessDto {

  private UUID uuid;

  private String message;

  private String username;

  private String city;

}
