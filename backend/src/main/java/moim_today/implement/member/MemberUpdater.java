package moim_today.implement.member;

import moim_today.domain.certification.Certification;
import moim_today.dto.member.ProfileUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.implement.certification.password.PasswordCertificationFinder;
import moim_today.implement.department.department.DepartmentFinder;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static moim_today.global.constant.MailConstant.PASSWORD_CERTIFICATION_FAIL;

@Implement
public class MemberUpdater {

    private final MemberRepository memberRepository;
    private final PasswordCertificationFinder passwordCertificationFinder;
    private final DepartmentFinder departmentFinder;
    private final PasswordEncoder passwordEncoder;

    public MemberUpdater(final MemberRepository memberRepository,
                         final PasswordCertificationFinder passwordCertificationFinder,
                         final DepartmentFinder departmentFinder,
                         final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordCertificationFinder = passwordCertificationFinder;
        this.departmentFinder = departmentFinder;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updatePassword(final long memberId, final String newPassword) {
        MemberJpaEntity memberJpaEntity = memberRepository.getById(memberId);
        memberJpaEntity.updatePassword(passwordEncoder, newPassword);
    }

    @Transactional
    public void recoverPassword(final String passwordToken, final String newPassword, final LocalDateTime now) {
        PasswordCertificationJpaEntity passwordCertificationJpaEntity =
                passwordCertificationFinder.getByCertificationToken(passwordToken);

        validateExpiredDateTime(passwordCertificationJpaEntity, now);

        MemberJpaEntity memberJpaEntity = memberRepository.getByEmail(passwordCertificationJpaEntity.getEmail());
        memberJpaEntity.updatePassword(passwordEncoder, newPassword);
    }

    @Transactional
    public void updateProfile(final long memberId,
                              final ProfileUpdateRequest profileUpdateRequest) {
        MemberJpaEntity memberJpaEntity = memberRepository.getById(memberId);
        memberJpaEntity.updateProfile(profileUpdateRequest);
    }

    private void validateExpiredDateTime(final PasswordCertificationJpaEntity passwordCertificationJpaEntity,
                                         final LocalDateTime now) {
        Certification certification = Certification.toDomain(passwordCertificationJpaEntity);
        certification.validateExpiredDateTime(now, PASSWORD_CERTIFICATION_FAIL.value());
    }
}
