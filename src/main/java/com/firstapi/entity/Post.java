package com.firstapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer postId;
    private String postTitle;
    private String postContent;
    private String imgName;
    private Date addedDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();



}
