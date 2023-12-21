package com.firstapi.repositories;

import com.firstapi.LearningApiApplication;
import com.firstapi.entity.Category;
import com.firstapi.entity.Post;
import com.firstapi.entity.User;
import com.firstapi.security.JwtAuthenticationEntryPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.*;



@DataJpaTest

class PostRepoTest {

private final Logger logger = LoggerFactory.getLogger(PostRepo.class);

@Autowired
private PostRepo postRepo;

@Autowired
private UserRepo userRepo;
@Autowired
private CategoryRepo categoryRepo;
private User user;
private Category category;
private Post post;
private Post post2;

// Run before every test
    @BeforeEach
    void setUp(){
// first post object
    post = new Post();
    post.setPostTitle("developer");
    post.setPostContent("");

// Second
    post2 = new Post();
    post2.setPostTitle("developer");
}
// getPostByUser
    @Test
    void givenUserObject_whenFind_thenReturnPosts(){

    user = new User();
    user.setName("");

    post.setUser(user);// set user for post, so it ,return post on the basis of user

    /*Post post1 = new Post().builder() .postTitle("Software").postContent("building software").user(user).build();
*/
    User savedUser = userRepo.save(user);
    Post savedPost = postRepo.save(post);// this is necessary to save otherwise post is not saved into the list
   /* Post savedPost = postRepo.save(post1);*/

    List<Post> posts = postRepo.findByUser(savedUser);
    logger.info("{}",posts);// getting info in console

    assertThat(posts).isNotNull();
}
// getPostByCategory
    @Test
    void givenCategoryObject_whenFind_thenReturnPosts(){
    category = new Category().builder().categoryTitle("software")
            .categoryDescription("posts related to software and useful tips for them")
            .build();

    post.setCategory(category);

    Category category1 = categoryRepo.save(category);
    Post savedPost = postRepo.save(post);
    List<Post> postList = postRepo.findByCategory(category1);
    logger.info("{}",postList);

   assertThat(postList).isNotNull();
   assertThat(postList.size()).isGreaterThan(0);
}
    @Test
    void givenTitle_whenFind_thenReturnPost(){

    Post save = postRepo.save(post);
    Post save2 = postRepo.save(post2);

    List<Post> testPost  = postRepo.findByPostTitleContaining("developer");
    logger.info("{}",testPost);


    assertThat(testPost).isNotEmpty();
    assertThat(testPost.get(0)).isEqualTo(save);
    assertThat(testPost.get(1)).isEqualTo(save2);

}
    @AfterEach
    void tearDown(){
 /*   userRepo.deleteAll();
    postRepo.deleteAll();
    categoryRepo.deleteAll();
*/
    user = null;
    category = null;
    post = null;
    post2 = null;
}

}