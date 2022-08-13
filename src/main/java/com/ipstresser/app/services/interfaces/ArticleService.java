package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.models.service.ArticleServiceModel;

import java.util.List;

public interface ArticleService {

    ArticleServiceModel getArticleById(String id);

    List<ArticleServiceModel> getAllArticles();

    void deleteArticleById(String id);
}
