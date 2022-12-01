package boardex.newboard.service;

import boardex.newboard.domain.Post;

import java.util.List;

public interface PostService {

    Long savePost(Post post);

    List<Post> findAll();
}
