package com.ipstresser.app.init;

import com.ipstresser.app.domain.entities.Article;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.repositories.ArticleRepository;
import com.ipstresser.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class ArticleInit implements CommandLineRunner {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleInit(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = this.userRepository.findUserByUsername("vladimir").orElse(null);

        Article article = new Article("Our website is here!", "Welcome to our website. Here you can find various services that will help you test your resources.", user,
                LocalDateTime.now(ZoneId.systemDefault()), "https://images.idgesg.net/images/article/2020/05/server_racks_close-up_perspective_shot_by_monsitj_gettyimages-918951042_cso_nw_2400x1600-100841600-large.jpg");

        this.articleRepository.save(article);
    }
}
