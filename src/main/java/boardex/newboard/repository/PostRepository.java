package boardex.newboard.repository;

import boardex.newboard.domain.Post;

import java.util.List;

public interface PostRepository {

    Long save(Post post);

    List<Post> findAll();

    Post findById(Long postId);

}
