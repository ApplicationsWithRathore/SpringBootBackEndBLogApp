package com.firstapi.services;

import com.firstapi.payloads.CommentDto;
import jakarta.persistence.criteria.CriteriaBuilder;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer pId);
    CommentDto updateComment(CommentDto commentDto, Integer cId);
    void deleteComment(Integer cId);


}
