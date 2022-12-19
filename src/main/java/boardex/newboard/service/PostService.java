package boardex.newboard.service;

import boardex.newboard.domain.Post;
import boardex.newboard.repository.SearchCondition;

import java.util.List;

public interface PostService {

    Long savePost(Post post);

    List<Post> findAll();

    Long updatePost(Long postId, String title, String content);

    Post findById(Long postId);

    Post findFetchMember(Long postId);

    List<Post> findAllFetch();

    List<Post> findAllFetchDynamic(SearchCondition cond);

}
