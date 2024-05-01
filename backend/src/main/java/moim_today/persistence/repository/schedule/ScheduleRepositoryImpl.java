package moim_today.persistence.repository.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.domain.schedule.Schedule;
import moim_today.dto.schedule.QScheduleResponse;
import moim_today.dto.schedule.ScheduleResponse;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.schedule.QScheduleJpaEntity;
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
import static moim_today.global.constant.exception.ScheduleExceptionConstant.*;
import static moim_today.persistence.entity.schedule.QScheduleJpaEntity.*;


@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;
    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(final ScheduleJpaRepository scheduleJpaRepository,
                                  final JPAQueryFactory queryFactory,
                                  final JdbcTemplate jdbcTemplate) {
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.queryFactory = queryFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleJpaEntity getById(final long scheduleId) {
        return scheduleJpaRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND.message()));
    }

    @Override
    public List<ScheduleJpaEntity> findAllByMemberId(final long memberId) {
        return scheduleJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<ScheduleResponse> findAllByDateTime(final long memberId, final LocalDateTime startDateTime,
                                                    final LocalDateTime endDateTime) {
        return queryFactory.select(
                        new QScheduleResponse(
                                scheduleJpaEntity.id,
                                scheduleJpaEntity.meetingId,
                                scheduleJpaEntity.scheduleName,
                                scheduleJpaEntity.dayOfWeek,
                                scheduleJpaEntity.startDateTime,
                                scheduleJpaEntity.endDateTime
                        ))
                .from(scheduleJpaEntity)
                .where(
                        scheduleJpaEntity.memberId.eq(memberId)
                                .and(scheduleJpaEntity.startDateTime.goe(startDateTime))
                                .and(scheduleJpaEntity.startDateTime.loe(endDateTime))
                                .and(scheduleJpaEntity.endDateTime.goe(startDateTime))
                                .and(scheduleJpaEntity.endDateTime.loe(endDateTime))
                )
                .fetch();
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
    public boolean exists(final ScheduleJpaEntity entity) {
        return queryFactory.selectFrom(scheduleJpaEntity)
                .where(
                        scheduleJpaEntity.memberId.eq(entity.getMemberId())
                                .and(scheduleJpaEntity.startDateTime.before(entity.getEndDateTime()))
                                .and(scheduleJpaEntity.endDateTime.after(entity.getStartDateTime()))
                )
                .fetchFirst() != null;
    }

    @Override
    public boolean existsExcludeEntity(final long scheduleId, final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        return queryFactory
                .selectFrom(scheduleJpaEntity)
                .where(
                        scheduleJpaEntity.memberId.eq(memberId)
                                .and(scheduleJpaEntity.id.ne(scheduleId))
                                .and(scheduleJpaEntity.startDateTime.before(scheduleUpdateRequest.endDateTime())
                                        .and(scheduleJpaEntity.endDateTime.after(scheduleUpdateRequest.startDateTime())))
                )
                .fetchFirst() != null;
    }

    @Override
    public void delete(final ScheduleJpaEntity scheduleJpaEntity) {
        scheduleJpaRepository.delete(scheduleJpaEntity);
    }

    @Override
    public long count() {
        return scheduleJpaRepository.count();
    }
}
