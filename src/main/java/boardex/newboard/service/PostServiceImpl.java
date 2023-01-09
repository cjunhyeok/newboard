package boardex.newboard.service;

import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import boardex.newboard.repository.MemberRepository;
import boardex.newboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Long savePost(Long memberId, String content, String title) {
        //
        Member findMember = memberRepository.findById(memberId);

        Post post = new Post();
        post.createPost(findMember, content, title);

        postRepository.save(post);
        return post.getId();
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public void updatePost(Long postId, String title, String content) {

        Post findPost = postRepository.findById(postId);
        findPost.updatePost(title, content);
    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Post findFetchMember(Long postId) {
        return postRepository.findFetchMember(postId);
    }

    @Override
    public List<Post> findAllFetch() {
        return postRepository.findAllFetch();
    }

    @Override
    public List<Post> findAllFetchDynamic(String cond, String keyword, Long page) {
        return postRepository.findAllFetchDynamic(cond, keyword, page);
    }

    @Override
    public Post findByIdFetchMemberComment(Long postId) {
        return postRepository.findByIdWithComment(postId);
    }

}
