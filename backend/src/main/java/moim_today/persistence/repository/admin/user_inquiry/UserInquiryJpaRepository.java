package moim_today.persistence.repository.admin.user_inquiry;

import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInquiryJpaRepository extends JpaRepository<UserInquiryJpaEntity, Long> {

    List<UserInquiryJpaEntity> findAllByUniversityIdOrderByCreatedAtDesc(final long universityId);
}
