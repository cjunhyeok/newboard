package boardex.newboard.repository;

import boardex.newboard.domain.Post;

import java.util.List;

public interface PostRepository {

    Long save(Post post);

    List<Post> findAll();

    Post findById(Long postId);

    Post findFetchMember(Long postId);

    List<Post> findAllFetch();

    List<Post> findAllFetchDynamic(String cond, String keyword, Long page);

    Post findByIdWithComment(Long postId);

    Long countAllPost();

}
