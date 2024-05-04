package moim_today.persistence.repository.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;

import java.util.List;

public interface JoinedMoimRepository {

    List<Long> findAllJoinedMemberId(final long moimId);

    void deleteAllByMoimId(final long moimId);

    long count();

    JoinedMoimJpaEntity save(final JoinedMoimJpaEntity joinedMoimJpaEntity);

    List<JoinedMoimJpaEntity> findJoinMembersByMoimId(final long moimId);

    void deleteMoimMember(long moimId, long memberId);
}
