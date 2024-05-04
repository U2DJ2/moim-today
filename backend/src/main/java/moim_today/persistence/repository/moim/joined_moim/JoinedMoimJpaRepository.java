package moim_today.persistence.repository.moim.joined_moim;

import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinedMoimJpaRepository extends JpaRepository<JoinedMoimJpaEntity, Long> {

    List<JoinedMoimJpaEntity> findMembersByMoimId(final long moimId);

    void deleteAllByMoimId(final long moimId);

    void deleteByMoimIdAndMemberId(final long moimId, final long memberId);
}
