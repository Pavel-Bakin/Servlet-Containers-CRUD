package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong postIdCounter = new AtomicLong(1);

    public Map<Long, Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postIdCounter.getAndIncrement());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
