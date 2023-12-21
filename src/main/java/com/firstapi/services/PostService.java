package com.firstapi.services;

import com.firstapi.payloads.PostDto;
import com.firstapi.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto create(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer id);
    PostResponse getAllPost(Integer pageNumber, Integer PageSize,String sortBy);
    PostDto getById(Integer id);

    List<PostDto> getByCategory(Integer categoryId);
    List<PostDto> getByUser(Integer userId);

    List<PostDto> searchPost(String keyword);
}
