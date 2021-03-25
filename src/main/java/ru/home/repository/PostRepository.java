package ru.home.repository;

import ru.home.exception.NotFoundException;
import ru.home.model.Post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class PostRepository {
    private static int totalPosts;

    private List<Post> repositoryData;

    public PostRepository() {
        this.repositoryData = new ArrayList<>();
    }

    public List<Post> all() {
        return repositoryData;
    }

    public Optional<Post> getById(long id) {
        Optional<Post> value = repositoryData.stream().filter(p -> p.getId() == id).findFirst();
        return value;
    }

    public synchronized Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(totalPosts);
            repositoryData.add(post);
            totalPosts++;
            return post;
        }
        boolean isExist = false;
        for (Post repoPost : repositoryData) {
            if (post.getId() == repoPost.getId()) {
                repoPost.setContent(post.getContent());
                isExist = true;
            }
        }
        if (isExist == false) {
            throw new NotFoundException("Couldn`t find post with id = " + post.getId() + " for update...");
        }
        return post;
    }

    public synchronized void removeById(long id) {
        Iterator<Post> it = repositoryData.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                break;
            }
        }
    }

}
