package com.mfano.moe.security.models;

import com.mfano.moe.security.CommonObject;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class UserStatus extends CommonObject {

}
