package boardex.newboard.repository;

import boardex.newboard.domain.Member;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long id);
}
