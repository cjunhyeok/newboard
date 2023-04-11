package boardex.newboard.service;

import boardex.newboard.domain.Comment;
import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import boardex.newboard.repository.CommentRepository;
import boardex.newboard.repository.MemberRepository;
import boardex.newboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public Long saveComment(Long memberId, Long postId, String content) {

        log.info("in service");
        log.info("member : {} post : {} content : {}", memberId, postId, content);
        Member findMember = memberRepository.findById(memberId);
        Post findPost = postRepository.findById(postId);

        Comment comment = new Comment();
        comment.createComment(findMember, findPost, content);

        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateComment(Long id, String content) {
        Comment findComment = commentRepository.findById(id);
        findComment.updateComment(content);
    }

}
