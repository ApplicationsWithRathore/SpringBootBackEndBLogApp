package com.firstapi.serviceimpl;

import com.firstapi.entity.Comment;
import com.firstapi.entity.Post;
import com.firstapi.exceptionhandler.ResourceNotFoundException;
import com.firstapi.payloads.CommentDto;
import com.firstapi.repositories.CommentRepo;
import com.firstapi.repositories.PostRepo;
import com.firstapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentImpl implements CommentService {
 @Autowired
  private PostRepo postRepo;
 @Autowired
  private CommentRepo commentRepo;
 @Autowired
 private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer pId) {
       Post post = this.postRepo.findById(pId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",pId));
      Comment comment = this.modelMapper.map(commentDto, Comment.class);
      comment.setPost(post);
      Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer cId) {
       Comment comment = this.commentRepo.findById(cId).orElseThrow(()->new ResourceNotFoundException("Comment","CommentId",cId));
       comment.setContent(commentDto.getContent());
       Comment updatedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(updatedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer cId) {
    Comment comment = this.commentRepo.findById(cId).orElseThrow(()->new ResourceNotFoundException("Comment" , "CommentId", cId));
        this.commentRepo.delete(comment);
    }
}
