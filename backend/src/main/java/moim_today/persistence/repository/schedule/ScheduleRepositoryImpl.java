package moim_today.persistence.repository.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.NumberConstant.SCHEDULE_MEETING_ID;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(final ScheduleJpaRepository scheduleJpaRepository,
                                  final JdbcTemplate jdbcTemplate) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final ScheduleJpaEntity scheduleJpaEntity) {
        scheduleJpaRepository.save(scheduleJpaEntity);
    }

    // jdbc batch update
    @Override
    public void batchUpdate(final TimeTableSchedulingTask timeTableSchedulingTask) {
        LocalDateTime now = LocalDateTime.now();
        List<Schedule> schedules = timeTableSchedulingTask.schedules();
        long memberId = timeTableSchedulingTask.memberId();
        String sql = "INSERT INTO schedule (member_id, meeting_id, schedule_name, day_of_week, start_date_time, end_date_time, created_at, last_modified_at)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Schedule schedule = schedules.get(i);
                ScheduleJpaEntity entity = schedule.toEntity(memberId, SCHEDULE_MEETING_ID.value());
                ps.setLong(1, entity.getMemberId());
                ps.setLong(2, entity.getMeetingId());
                ps.setString(3, entity.getScheduleName());
                ps.setString(4, entity.getDayOfWeek().toString());
                ps.setTimestamp(5, Timestamp.valueOf(entity.getStartDateTime()));
                ps.setTimestamp(6, Timestamp.valueOf(entity.getEndDateTime()));
                ps.setTimestamp(7, Timestamp.valueOf(now));
                ps.setTimestamp(8, Timestamp.valueOf(now));
            }

            @Override
            public int getBatchSize() {
                return schedules.size();
            }
        });
    }

    @Override
    public long count() {
        return scheduleJpaRepository.count();
    }
}
