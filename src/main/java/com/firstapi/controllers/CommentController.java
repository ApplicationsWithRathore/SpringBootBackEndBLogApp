package com.firstapi.controllers;

import com.firstapi.entity.Comment;
import com.firstapi.payloads.ApiResponse;
import com.firstapi.payloads.CommentDto;
import com.firstapi.services.CommentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{pId}/comments")
   public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto, @PathVariable Integer pId){
        CommentDto commentDtos = this.commentService.createComment(commentDto, pId);
        return new ResponseEntity<>(commentDtos, HttpStatus.CREATED);
    }

    @PutMapping("comment/{cId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto , @PathVariable Integer cId){
        CommentDto updatedComment = this.commentService.updateComment(commentDto,cId);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);

    }
    @DeleteMapping("comment/{cId}")
    public ResponseEntity<ApiResponse> deleted(@PathVariable Integer cId){
        this.commentService.deleteComment(cId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
    }
}
