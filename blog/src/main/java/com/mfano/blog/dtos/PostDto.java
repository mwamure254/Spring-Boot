package com.mfano.blog.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDto {
    private String title;
    private String slug;
    private String content;
    private String image;

    private Long userid;

    private Long categoryid;
}
