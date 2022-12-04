package boardex.newboard.repository;

import boardex.newboard.domain.Member;

import java.util.List;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    List<Member> findByNickName(String nickName);

    Member findByUserId(String userId);

}
