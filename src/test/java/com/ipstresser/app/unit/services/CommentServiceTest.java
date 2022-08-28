package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Comment;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.CommentServiceModel;
import com.ipstresser.app.repositories.CommentRepository;
import com.ipstresser.app.services.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;

    private CommentServiceModel commentServiceModel;

    private User user;

    @BeforeEach
    public void init() {
        this.comment = new Comment(4, "Amazing site,the best", null);
        this.commentServiceModel = new CommentServiceModel(4, "Amazing site,the best", null);
        this.user = new User();

    }

    @Test
    public void getAllCommentsShouldReturnAllComments() {
        List<Comment> comments = List.of(this.comment);
        Mockito.when(commentRepository.findAllByOrderByRateDesc()).thenReturn(comments);

        List<CommentServiceModel> result = this.commentService.getAllCommentsSortedByRatingDesc();

        assertEquals(1, result.size());
    }


}
