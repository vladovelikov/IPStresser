package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Article;
import com.ipstresser.app.domain.models.service.ArticleServiceModel;
import com.ipstresser.app.exceptions.ArticleNotFoundException;
import com.ipstresser.app.repositories.ArticleRepository;
import com.ipstresser.app.services.interfaces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArticleServiceModel getArticleById(String id) {
        Article article = this.articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("The article is not found."));

        return this.modelMapper.map(article, ArticleServiceModel.class);
    }

    @Override
    public List<ArticleServiceModel> getAllArticles() {
        return List.of(this.modelMapper.map(this.articleRepository.findAllByOrderByAddedOnDesc(), ArticleServiceModel.class));
    }

    @Override
    public void deleteArticleById(String id) {
        this.articleRepository.deleteById(id);
    }
}
