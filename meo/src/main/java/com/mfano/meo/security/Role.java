package com.mfano.meo.security;

import com.mfano.meo.models.CommonObject;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends CommonObject{

}
