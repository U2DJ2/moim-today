package moim_today.persistence.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.member.MemberProfileResponse;
import moim_today.dto.member.QMemberProfileResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static moim_today.global.constant.exception.MemberExceptionConstant.*;
import static moim_today.persistence.entity.department.QDepartmentJpaEntity.departmentJpaEntity;
import static moim_today.persistence.entity.member.QMemberJpaEntity.memberJpaEntity;
import static moim_today.persistence.entity.university.QUniversityJpaEntity.universityJpaEntity;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository,
                                final JPAQueryFactory queryFactory) {
        this.memberJpaRepository = memberJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public MemberJpaEntity save(final MemberJpaEntity entity) {
        return memberJpaRepository.save(entity);
    }

    @Override
    public MemberJpaEntity getByEmail(final String email) {
        return memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(EMAIL_PASSWORD_ERROR.message()));
    }

    @Override
    public Optional<MemberJpaEntity> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public void validateEmailExists(final String email) {
        memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(EMAIL_NOT_FOUND_ERROR.message()));
    }

    @Override
    public MemberJpaEntity getById(final long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND_ERROR.message()));
    }

    @Override
    public MemberProfileResponse getMemberProfile(final long memberId) {
        return queryFactory.select(new QMemberProfileResponse(
                        universityJpaEntity.universityName,
                        departmentJpaEntity.departmentName,
                        memberJpaEntity.email,
                        memberJpaEntity.username,
                        memberJpaEntity.studentId,
                        memberJpaEntity.birthDate,
                        memberJpaEntity.gender,
                        memberJpaEntity.memberProfileImageUrl
                ))
                .from(memberJpaEntity)
                .join(universityJpaEntity).on(memberJpaEntity.universityId.eq(universityJpaEntity.id))
                .join(departmentJpaEntity).on(memberJpaEntity.departmentId.eq(departmentJpaEntity.id))
                .where(memberJpaEntity.id.eq(memberId))
                .stream().findAny()
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND_ERROR.message()));
    }
}
