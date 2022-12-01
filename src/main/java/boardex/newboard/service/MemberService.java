package boardex.newboard.service;

import boardex.newboard.domain.Member;

import java.util.List;

public interface MemberService {

    Long join(Member member);

    List<Member> findMembers();

    Member findByUserId(Long userId);
}
