package moim_today.persistence.entity.schedule.schedule;

import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_FORBIDDEN;

@Getter
@Table(name = "schedule")
@Entity
public class ScheduleJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private long id;

    @Association
    private long memberId;

    @Association
    private long moimId;

    @Association
    private long meetingId;

    private String scheduleName;

    private String colorHex;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    protected ScheduleJpaEntity() {
    }

    @Builder
    private ScheduleJpaEntity(final long memberId, final long moimId, final long meetingId,
                              final String scheduleName, final String colorHex,
                              final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        this.memberId = memberId;
        this.moimId = moimId;
        this.meetingId = meetingId;
        this.scheduleName = scheduleName;
        this.colorHex = colorHex;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static ScheduleJpaEntity toEntity(final long memberId, final String moimTitle,
                                             final MeetingJpaEntity meetingJpaEntity) {
        return ScheduleJpaEntity.builder()
                .memberId(memberId)
                .moimId(meetingJpaEntity.getMoimId())
                .meetingId(meetingJpaEntity.getId())
                .scheduleName(moimTitle)
                .startDateTime(meetingJpaEntity.getStartDateTime())
                .endDateTime(meetingJpaEntity.getEndDateTime())
                .build();
    }

    public void updateSchedule(final ScheduleUpdateRequest scheduleUpdateRequest) {
        this.scheduleName = scheduleUpdateRequest.scheduleName();
        this.startDateTime = scheduleUpdateRequest.startDateTime();
        this.endDateTime = scheduleUpdateRequest.endDateTime();
    }

    public void validateMember(final long memberId) {
        if (this.memberId != memberId) {
            throw new ForbiddenException(SCHEDULE_FORBIDDEN.message());
        }
    }
}
