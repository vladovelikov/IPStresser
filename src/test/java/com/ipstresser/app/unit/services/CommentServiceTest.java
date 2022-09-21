package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Comment;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.CommentServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.exceptions.CommentNotFoundException;
import com.ipstresser.app.exceptions.PlanNotFoundException;
import com.ipstresser.app.repositories.CommentRepository;
import com.ipstresser.app.services.CommentServiceImpl;
import com.ipstresser.app.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;

    private CommentServiceModel commentServiceModel;

    private UserServiceModel user;

    @BeforeEach
    public void init() {
        this.comment = new Comment(4, "Amazing site,the best", null);
        this.commentServiceModel = new CommentServiceModel(4, "Amazing site,the best", null);
        this.user = new UserServiceModel();
        this.user.setUsername("vladimir");
        this.user.setComment(commentServiceModel);

        this.commentRepository.save(this.comment);
    }

    @Test
    public void getAllCommentsShouldReturnAllComments() {
        List<Comment> comments = List.of(comment);
        Mockito.when(commentRepository.findAllByOrderByRateDesc()).thenReturn(comments);
        Mockito.when(modelMapper.map(comments, CommentServiceModel[].class))
                .thenReturn(Arrays.asList(commentServiceModel).toArray(CommentServiceModel[]::new));

        List<CommentServiceModel> result = commentService.getAllCommentsSortedByRatingDesc();

        assertEquals(1, result.size());
    }

    @Test
    public void deleteCommentByIdShouldWorkCorrect() {
        Mockito.when(commentRepository.findById("1")).thenReturn(Optional.of(comment));
        this.commentService.deleteCommentById("1");

        Mockito.verify(commentRepository).delete(comment);
    }

    @Test
    public void deleteCommentByIdShouldThrowException_CommentNotFound() {
        assertThrows(CommentNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                commentService.deleteCommentById("2");
            }
        });
    }

    @Test
    public void hasUserAlreadyCommentedShouldWork() {
        Mockito.when(userService.getUserByUsername("vladimir")).thenReturn(user);
        assertTrue(commentService.hasUserAlreadyCommented("vladimir"));
    }

    @Test
    public void registerCommentShouldWorkCorrect() {
        Mockito.when(userService.getUserByUsername("vladimir")).thenReturn(user);
        Mockito.when(modelMapper.map(commentServiceModel, Comment.class)).thenReturn(comment);

        commentService.registerComment(commentServiceModel, "vladimir");

        Mockito.verify(commentRepository).save(comment);
    }


}
