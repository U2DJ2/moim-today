package moim_today.persistence.repository.member;

import moim_today.dto.member.MemberProfileResponse;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.persistence.entity.member.MemberJpaEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    MemberJpaEntity save(final MemberJpaEntity entity);

    MemberJpaEntity getByEmail(final String email);

    Optional<MemberJpaEntity> findByEmail(final String email);

    void validateEmailExists(final String email);

    void validateAlreadyExists(final String email);

    MemberJpaEntity getById(final long memberId);

    MemberProfileResponse getMemberProfile(final long memberId);

    List<MemberJpaEntity> findByIdIn(final List<Long> memberIds);

    List<MoimMemberResponse> findMembersWithJoinInfo(final List<Long> joinedMoimMemberIds, final long hostId);

    MemberSimpleResponse getHostProfileByMoimId(final long moimId);
}
