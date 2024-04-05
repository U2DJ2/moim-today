package booki_today.persistence.repository.time_table;

import booki_today.persistence.entity.time_table.TimeTableJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableJpaRepository extends JpaRepository<TimeTableJpaEntity, Long> {
}
