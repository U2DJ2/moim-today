package moim_today.persistence.repository.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.member.*;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.dto.moim.moim.QMoimMemberResponse;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static moim_today.global.constant.MemberConstant.DELETED_MEMBER_STUDENT_ID;
import static moim_today.global.constant.exception.MemberExceptionConstant.*;
import static moim_today.persistence.entity.department.QDepartmentJpaEntity.departmentJpaEntity;
import static moim_today.persistence.entity.member.QMemberJpaEntity.memberJpaEntity;
import static moim_today.persistence.entity.moim.joined_moim.QJoinedMoimJpaEntity.joinedMoimJpaEntity;
import static moim_today.persistence.entity.moim.moim.QMoimJpaEntity.moimJpaEntity;
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
    public void validateAlreadyExists(final String email) {
        if (memberJpaRepository.existsByEmail(email)) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL_ERROR.message());
        }
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

    @Override
    public List<MoimMemberResponse> findMoimMembers(final List<Long> moimMemberIds,
                                                    final long hostId, final long moimId) {
        return queryFactory.select(new QMoimMemberResponse(
                        memberJpaEntity.id.eq(hostId),
                        memberJpaEntity.id,
                        memberJpaEntity.username,
                        joinedMoimJpaEntity.createdAt,
                        memberJpaEntity.memberProfileImageUrl
                )).from(memberJpaEntity)
                .join(joinedMoimJpaEntity).on(joinedMoimJpaEntity.memberId.eq(memberJpaEntity.id))
                .where(joinedMoimJpaEntity.memberId.in(moimMemberIds)
                        .and(joinedMoimJpaEntity.moimId.eq(moimId)))
                .fetch();
    }

    @Override
    public MemberSimpleResponse getHostProfileByMoimId(final long moimId) {
        return queryFactory.select(new QMemberSimpleResponse(
                        memberJpaEntity.id,
                        memberJpaEntity.username,
                        memberJpaEntity.memberProfileImageUrl
                )).from(memberJpaEntity)
                .join(moimJpaEntity).on(moimJpaEntity.id.eq(moimId).and(moimJpaEntity.memberId.eq(memberJpaEntity.id)))
                .stream().findAny()
                .orElseThrow(() -> new NotFoundException(HOST_NOT_FOUND_ERROR.message()));
    }

    @Override
    public List<MemberResponse> findByUniversityIdAndDepartmentId(final long universityId, final long departmentId) {
        return queryFactory.select(
                        new QMemberResponse(
                                memberJpaEntity.id,
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
                .where(
                        memberJpaEntity.universityId.eq(universityId),
                        departmentFilter(departmentId),
                        memberJpaEntity.studentId.ne(DELETED_MEMBER_STUDENT_ID.value())
                )
                .fetch();
    }

    @Override
    public void deleteById(final long memberId) {
        memberJpaRepository.deleteById(memberId);
    }

    private BooleanExpression departmentFilter(final long departmentId) {
        if(departmentId == 0) {
            return null;
        }
        return memberJpaEntity.departmentId.eq(departmentId);
    }
}
