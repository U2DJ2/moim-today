package moim_today.persistence.repository.member;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import org.springframework.stereotype.Repository;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_PASSWORD_ERROR;
import static moim_today.global.constant.exception.MemberExceptionConstant.MEMBER_NOT_FOUND_ERROR;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public void save(final MemberJpaEntity entity) {
        memberJpaRepository.save(entity);
    }

    @Override
    public MemberJpaEntity getByEmail(final String email) {
        return memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(EMAIL_PASSWORD_ERROR.message()));
    }

    @Override
    public MemberJpaEntity getById(final long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND_ERROR.message()));
    }
}
