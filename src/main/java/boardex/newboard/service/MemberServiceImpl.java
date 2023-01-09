package boardex.newboard.service;

import boardex.newboard.domain.Member;
import boardex.newboard.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Override
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 nickname 체크
        member.encodePassword(passwordEncoder.encode(member.getUserPassword())); // 비밀번호 암호화
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
    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    // merge가 아닌 변경감지 사용
    @Override
    @Transactional
    public void updateMember(Long memberId, String userPassword, String nickname, String name, String address) {
        Member findMember = memberRepository.findById(memberId);
        findMember.updateMember(passwordEncoder.encode(userPassword), nickname, name, address);

    }

    @Override
    public Member login(String userId, String userPassword) {
        Member findMember = memberRepository.findByUserId(userId);

        if (findMember.getUserPassword().equals(userPassword)) {
            return findMember;
        } else{
            return null;
        }
    }

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByNickName(member.getNickName());
        log.info("duplicateCheck!");
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
}
