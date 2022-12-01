package boardex.newboard.service;

import boardex.newboard.domain.Member;
import boardex.newboard.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Autowired  // -> @RequiredArgsConstructor 대체가능
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    @Override
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 nickname 체크
        memberRepository.save(member);
        return member.getId();
    }

    // 모든 회원 찾기
    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // UserId 찾기
    @Override
    public Member findByUserId(Long userId) {
        return memberRepository.findByUserId(userId);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByNickName(member.getNickName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
}
