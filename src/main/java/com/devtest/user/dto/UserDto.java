package com.devtest.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.devtest.user.validator.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

  private static final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

  @ApiModelProperty(notes = "User name", required = true)
  @NotBlank(message = "username must not be blank")
  private String username;

  @ApiModelProperty(notes = "Password", required = true)
  @NotBlank(message = "password must not be blank")
  @Password
  private String password;

  @ApiModelProperty(notes = "User IP Address", required = true)
  @NotBlank(message = "ipAddress must not be blank")
  @Pattern(regexp = IPV4_PATTERN, message = "ipAddress is not valid")
  private String ipAddress;

}
