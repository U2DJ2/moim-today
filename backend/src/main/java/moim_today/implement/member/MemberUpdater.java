package moim_today.implement.member;

import moim_today.domain.certification_token.CertificationToken;
import moim_today.global.annotation.Implement;
import moim_today.implement.certification_token.CertificationTokenFinder;
import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Implement
public class MemberUpdater {

    private final MemberRepository memberRepository;
    private final CertificationTokenFinder certificationTokenFinder;
    private final PasswordEncoder passwordEncoder;

    public MemberUpdater(final MemberRepository memberRepository,
                         final CertificationTokenFinder certificationTokenFinder,
                         final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.certificationTokenFinder = certificationTokenFinder;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updatePassword(final long memberId, final String newPassword) {
        MemberJpaEntity memberJpaEntity = memberRepository.getById(memberId);
        memberJpaEntity.updatePassword(passwordEncoder, newPassword);
    }

    @Transactional
    public void recoverPassword(final String passwordToken, final String newPassword, final LocalDateTime now) {
        CertificationTokenJpaEntity certificationTokenJpaEntity =
                certificationTokenFinder.getByCertificationToken(passwordToken);

        validateExpiredDateTime(certificationTokenJpaEntity, now);

        MemberJpaEntity memberJpaEntity = memberRepository.getByEmail(certificationTokenJpaEntity.getEmail());
        memberJpaEntity.updatePassword(passwordEncoder, newPassword);
    }

    private void validateExpiredDateTime(final CertificationTokenJpaEntity certificationTokenJpaEntity,
                                         final LocalDateTime now) {
        CertificationToken certificationToken = CertificationToken.toDomain(certificationTokenJpaEntity);
        certificationToken.validateExpiredDateTime(now);
    }
}