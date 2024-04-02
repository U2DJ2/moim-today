package booki_today.persistence.repository.member;

import booki_today.global.error.NotFoundException;
import booki_today.persistence.entity.member.MemberJpaEntity;
import org.springframework.stereotype.Repository;

import static booki_today.global.constant.MemberExceptionConstant.EMAIL_PASSWORD_ERROR;

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
}
