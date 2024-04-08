package moim_today.implement.member;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MemberUpdater {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberUpdater(final MemberRepository memberRepository, final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updatePassword(final long memberId, final String newPassword) {
        MemberJpaEntity memberJpaEntity = memberRepository.getById(memberId);
        memberJpaEntity.updatePassword(passwordEncoder, newPassword);
    }
}
