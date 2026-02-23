package com.mfano.moe.models;

import com.mfano.moe.security.model.CommonObject;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class ICategory extends CommonObject{

}
