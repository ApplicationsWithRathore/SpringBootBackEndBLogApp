package com.firstapi.payloads;

import com.firstapi.entity.Category;
import com.firstapi.entity.Comment;
import com.firstapi.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
@ToString
@Setter
@NoArgsConstructor
public class PostDto {
   private Integer id;
    private String postTitle;
    private String postContent;
    private String imgName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<Comment> comments = new HashSet<>();



}
