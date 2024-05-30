package moim_today.persistence.repository.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MoimJpaRepository extends JpaRepository<MoimJpaEntity, Long> {

    List<MoimJpaEntity> findAllByUniversityId(final long universityId);
}

