package ru.netology.confiq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

@Configuration
@ComponentScan(basePackages = "ru.netology")
@EnableWebMvc
public class AppConfig {

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostController postController() {
        return new PostController(postService());
    }
}
