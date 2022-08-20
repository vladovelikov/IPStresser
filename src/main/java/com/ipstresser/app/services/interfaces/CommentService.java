package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.CommentServiceModel;

import java.util.List;

public interface CommentService {

    boolean hasUserAlreadyCommented(String username);

    List<CommentServiceModel> getAllCommentsSortedByRatingDesc();

    void registerComment(CommentServiceModel commentServiceModel, String username);

    void deleteCommentById(String id);
}
