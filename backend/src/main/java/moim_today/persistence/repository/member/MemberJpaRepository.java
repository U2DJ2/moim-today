package moim_today.persistence.repository.member;

import moim_today.persistence.entity.member.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    Optional<MemberJpaEntity> findByEmail(final String email);

    boolean existsByEmail(final String email);
}
