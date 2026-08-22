package com.mfano.blog.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseModel{
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "postid", insertable = false, updatable = false)
    private Post post;
    private Long postid;

}
