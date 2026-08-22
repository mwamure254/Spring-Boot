package com.mfano.blog.models;

import com.mfano.blog.models.security.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseModel {
    private String title;
    private String slug;
    @Lob private String content;
    private String image;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
     private User author;
    private Long userid;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "categoryid", insertable = false, updatable = false)
    private Category category;
    private Long categoryid;

}
