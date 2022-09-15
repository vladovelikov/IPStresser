package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Article;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.ArticleServiceModel;
import com.ipstresser.app.exceptions.ArticleNotFoundException;
import com.ipstresser.app.repositories.ArticleRepository;
import com.ipstresser.app.services.interfaces.ArticleService;
import com.ipstresser.app.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArticleServiceModel getArticleById(String id) {
        Article article = this.articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("The article is not found."));

        return this.modelMapper.map(article, ArticleServiceModel.class);
    }

    @Override
    public List<ArticleServiceModel> getAllArticles() {
        return List.of(this.modelMapper.map(this.articleRepository.findAllByOrderByAddedOnDesc(), ArticleServiceModel[].class));
    }

    @Override
    public void deleteArticleById(String id) {
        this.articleRepository.deleteById(id);
    }

    @Override
    public void registerArticle(ArticleServiceModel articleServiceModel, String username) {
        User user = this.modelMapper.map(this.userService.getUserByUsername(username), User.class);
        Article article = this.modelMapper.map(articleServiceModel, Article.class);

        article.setAddedOn(LocalDateTime.now(ZoneId.systemDefault()));
        article.setAuthor(user);

        this.articleRepository.save(article);
    }
}
