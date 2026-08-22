package com.mfano.blog.dtos;

import java.util.HashSet;
import java.util.Set;

import com.mfano.blog.models.security.Role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
  private String email;
  private String password;
  private String fin;
  private String lan;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

}
