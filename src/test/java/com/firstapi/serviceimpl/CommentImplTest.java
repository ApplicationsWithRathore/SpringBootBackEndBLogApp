package com.firstapi.serviceimpl;

import com.firstapi.entity.Comment;
import com.firstapi.entity.Post;
import com.firstapi.payloads.CommentDto;
import com.firstapi.repositories.CommentRepo;
import com.firstapi.repositories.PostRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentImplTest {
    @Mock
    CommentRepo commentRepo;
    @Mock
    PostRepo postRepo;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    CommentImpl commentService;
    private Logger logger = LoggerFactory.getLogger(CommentImplTest.class);

    @Test
    void createComment() {

        Post post = Post.builder().postId(1).postTitle("UnitTesting").build();


        Comment comment1 = Comment.builder().content("nice").post(post).build();
        CommentDto commentDto = CommentDto.builder().content("nice").build();

        when(postRepo.findById(1)).thenReturn(Optional.of(post));
        when(modelMapper.map(commentDto,Comment.class)).thenReturn(comment1);
        when(commentRepo.save(comment1)).thenReturn(comment1);
        when(modelMapper.map(comment1,CommentDto.class)).thenReturn(commentDto);

        CommentDto commentDto1 = commentService.createComment(commentDto,1);
        logger.info("{}",commentDto1);

        assertThat(commentDto1).isEqualTo(commentDto);
        assertThat(commentDto1).hasFieldOrPropertyWithValue("content","nice");



    }

    @Test
    void updateComment() {
        Comment comment1 = Comment.builder().cId(1).content("nice").build();
        CommentDto commentDto = CommentDto.builder().content("Thanks").build();

        when(commentRepo.findById(1)).thenReturn(Optional.of(comment1));
        when(commentRepo.save(comment1)).thenReturn(comment1);
        when(modelMapper.map(comment1,CommentDto.class)).thenReturn(commentDto);

        CommentDto updated = commentService.updateComment(commentDto,1);

        assertThat(updated).isEqualTo(commentDto);
        assertThat(updated).isNotNull();
        logger.info("{}",updated);


    }

    @Test
    void deleteComment() {
        Comment comment = Comment.builder().cId(1).content("Thanks").build();
        when(commentRepo.findById(1)).thenReturn(Optional.of(comment));
        commentService.deleteComment(1);

        verify(commentRepo).delete(comment);
    }
}