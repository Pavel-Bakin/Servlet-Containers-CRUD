package ru.netology.controller;

import ru.netology.exception.NotFoundException;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    public static final String GET_METHOD = "GET";   // Добавляем константу для GET метода
    public static final String POST_METHOD = "POST"; // Добавляем константу для POST метода
    public static final String DELETE_METHOD = "DELETE"; // Добавляем константу для DELETE метода

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        try {
            response.getWriter().print(gson.toJson(data));
        } catch (IOException e) {
            // Обработка исключения, например, вывод в лог
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void getById(long id, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var post = service.getById(id);
            response.getWriter().print(gson.toJson(post));
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            service.removeById(id);
            response.getWriter().print(gson.toJson("Post with id " + id + " has been removed"));
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void updateById(long id, Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        post.setId(id);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }
}
