package com.mfano.mpos.dtos;

import java.util.HashSet;
import java.util.Set;

import com.mfano.mpos.models.security.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
  private String email;
  private String password;
  private String fin;
  private String lan;
  private Long branch;
  
  private Set<Role> roles = new HashSet<>();

}
