package boardex.newboard.service;

import boardex.newboard.domain.Post;

import java.util.List;

public interface PostService {

    Long savePost(Post post);

    List<Post> findAll();

    void updatePost(Long postId, String title, String content);

    Post findById(Long postId);

    Post findFetchMember(Long postId);

    List<Post> findAllFetch();

    List<Post> findAllFetchDynamic(String cond, String keyword, Long page);

    Post findByIdFetchMemberComment(Long postId);

}
