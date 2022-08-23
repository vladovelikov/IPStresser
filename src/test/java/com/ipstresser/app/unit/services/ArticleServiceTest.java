package com.ipstresser.app.unit.services;

import com.ipstresser.app.domain.entities.Article;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.domain.models.service.ArticleServiceModel;
import com.ipstresser.app.domain.models.service.UserServiceModel;
import com.ipstresser.app.repositories.ArticleRepository;
import com.ipstresser.app.services.ArticleServiceImpl;
import com.ipstresser.app.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private ModelMapper modelMapper;
    private User user;
    private Article article;
    private ArticleServiceModel articleServiceModel;
    private UserServiceModel userServiceModel;

    @BeforeEach
    public void init() {
        this.user = new User();
        this.user.setId("1");
        this.user.setUsername("vladimir");

        this.article = new Article();
        this.article.setId("1");
        this.article.setTitle("Test");
        this.article.setAuthor(this.user);

        this.articleServiceModel = new ArticleServiceModel();
        this.articleServiceModel.setId("1");
        this.articleServiceModel.setTitle("Test");
        this.articleServiceModel.setAuthor(this.user);

        this.userServiceModel = new UserServiceModel();
    }

    @Test
    public void getAllArticlesShouldReturnCorrect() {
        Mockito.when(this.articleRepository.findAllByOrderByAddedOnDesc()).thenReturn(List.of(this.article));
        Mockito.when(this.modelMapper.map(this.articleRepository.findAllByOrderByAddedOnDesc(), ArticleServiceModel[].class))
                .thenReturn(List.of(articleServiceModel).toArray(ArticleServiceModel[]::new));
        List<ArticleServiceModel> actual = this.articleService.getAllArticles();

        assertEquals(1, actual.size());
        assertEquals(this.articleServiceModel, actual.get(0));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "ROOT"})
    public void deleteArticleByIdShouldWork() {
        this.articleRepository.deleteById("1");
        Mockito.verify(this.articleRepository).deleteById("1");
    }

    @Test
    public void getArticleByIdShouldReturnArticleIdCorrect() {
        Mockito.when(this.articleRepository.findById("1")).thenReturn(Optional.of(this.article));
        Mockito.when(this.modelMapper.map(
                this.articleRepository.findById("1"), ArticleServiceModel.class)).thenReturn(this.articleServiceModel);

        ArticleServiceModel actual = this.articleService.getArticleById("1");

        assertEquals(this.articleServiceModel, actual);
    }

    @Test
    public void registerArticleShouldWork() {
        Mockito.when(this.userService.getUserByUsername("vladimir")).thenReturn(this.userServiceModel);
        Mockito.when(this.modelMapper.map(this.userServiceModel, User.class)).thenReturn(this.user);

        this.articleService.registerArticle(this.articleServiceModel, "vladimir");
        Mockito.verify(this.articleRepository).save(this.article);

    }
}
