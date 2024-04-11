package moim_today.persistence.repository.time_table;

import org.springframework.stereotype.Repository;

@Repository
public class TimeTableRepositoryImpl implements TimeTableRepository {

    private final TimeTableJpaRepository timeTableJpaRepository;

    public TimeTableRepositoryImpl(final TimeTableJpaRepository timeTableJpaRepository) {
        this.timeTableJpaRepository = timeTableJpaRepository;
    }
}
