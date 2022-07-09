package com.ipstresser.app.init;

import com.ipstresser.app.domain.entities.Comment;
import com.ipstresser.app.domain.entities.User;
import com.ipstresser.app.repositories.CommentRepository;
import com.ipstresser.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentsInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public CommentsInit(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.commentRepository.count() == 0) {
            User user = this.userRepository.findUserByUsername("vladimir").orElse(null);
            Comment firstComment = new Comment(5, "The best IP stresser on the market", user);
            Comment secondComment = new Comment(5, "Now nothing can stop me!", user);

            this.commentRepository.saveAll(List.of(firstComment, secondComment));
        }
    }
}
