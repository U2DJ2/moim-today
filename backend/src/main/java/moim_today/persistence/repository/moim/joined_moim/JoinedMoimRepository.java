package moim_today.persistence.repository.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;

import java.util.List;

public interface JoinedMoimRepository {

    List<Long> findAllJoinedMemberId(final long moimId);

    void deleteAllByMoimId(final long moimId);

    long count();

    JoinedMoimJpaEntity save(final JoinedMoimJpaEntity joinedMoimJpaEntity);

    List<JoinedMoimJpaEntity> findJoinMembersByMoimId(final long moimId);

    void deleteMoimMember(final long moimId, final long memberId);

    boolean isJoining(final long moimId, final long memberId);

    boolean existsByMoimIdAndMemberId(final long moimId, final long memberId);
}
