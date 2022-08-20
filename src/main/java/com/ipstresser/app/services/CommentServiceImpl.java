package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Comment;
import com.ipstresser.app.domain.models.service.CommentServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.repositories.CommentRepository;
import com.ipstresser.app.services.interfaces.CommentService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean hasUserAlreadyCommented(String username) {
        return this.userService.getUserByUsername(username).getComment() != null;
    }

    @Override
    public List<CommentServiceModel> getAllCommentsSortedByRatingDesc() {
        return List.of(this.modelMapper.map(
                this.commentRepository.findAllByOrderByRateDesc(), CommentServiceModel.class));
    }

    @Override
    public void registerComment(CommentServiceModel commentServiceModel, String username) {
        UserServiceModel userServiceModel = this.userService.getUserByUsername(username);
        commentServiceModel.setAuthor(userServiceModel);

        Comment comment = this.modelMapper.map(commentServiceModel, Comment.class);
        this.commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(String id) {
        this.commentRepository.deleteById(id);
    }
}
