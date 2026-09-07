package com.mfano.mpos.dtos;

import java.util.HashSet;
import java.util.Set;

import com.mfano.mpos.models.security.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
public class UserDto {
  @Email 
  @NotBlank 
  private String email;
  @NotBlank
  private String password;
  @NotNull 
  private Long branch;
  @NotNull 
  private Long role;

}
