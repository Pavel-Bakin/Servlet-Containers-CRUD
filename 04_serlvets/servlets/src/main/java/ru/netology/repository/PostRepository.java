package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private final AtomicLong postIdCounter = new AtomicLong(1);

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postIdCounter.getAndIncrement());
            posts.add(post);
        } else {
            Optional<Post> existingPost = getById(post.getId());
            if (existingPost.isPresent()) {
                int index = posts.indexOf(existingPost.get());
                posts.set(index, post);
            }
        }
        return post;
    }

    public void removeById(long id) {
        posts.removeIf(post -> post.getId() == id);
    }
}