package com.mfano.blog.models.security;

import com.mfano.blog.models.BaseModel;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseModel{
    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

}
