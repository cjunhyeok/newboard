package boardex.newboard.security.service;

import boardex.newboard.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberContext extends User {

    private final Member member;

    public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUserId(), member.getUserPassword(), authorities);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
