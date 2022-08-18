package com.ipstresser.app.services;

import com.ipstresser.app.repositories.CommentRepository;
import com.ipstresser.app.services.interfaces.CommentService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
