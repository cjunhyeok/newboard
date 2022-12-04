package boardex.newboard.service;

import boardex.newboard.domain.Member;

import java.util.List;

public interface MemberService {

    Long join(Member member);

    List<Member> findMembers();

    Member findByUserId(String userId);

    void updateMember(Long memberId, Member member);

    Member login(String userId, String userPassword);
}
