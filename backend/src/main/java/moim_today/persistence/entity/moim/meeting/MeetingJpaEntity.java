package moim_today.persistence.entity.moim.meeting;

import moim_today.domain.regular_moim.enums.AttendanceStatus;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "meeting")
@Entity
public class MeetingJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private long id;

    @Association
    private long moimId;

    @Association
    private long memberId;

    private String agenda;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    protected MeetingJpaEntity() {
    }

    @Builder
    private MeetingJpaEntity(final long moimId, final long memberId, final String agenda,
                            final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        this.moimId = moimId;
        this.memberId = memberId;
        this.agenda = agenda;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
