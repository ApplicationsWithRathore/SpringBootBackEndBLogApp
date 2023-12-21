package com.firstapi.repositories;

import com.firstapi.entity.Category;
import com.firstapi.entity.Post;
import com.firstapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post>  findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByPostTitleContaining(String postTitle);
}
