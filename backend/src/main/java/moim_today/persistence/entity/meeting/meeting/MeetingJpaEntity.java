package moim_today.persistence.entity.meeting.meeting;

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

    private String agenda;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String place;

    protected MeetingJpaEntity() {
    }

    @Builder
    private MeetingJpaEntity(final long moimId, final String agenda, final LocalDateTime startDateTime,
                             final LocalDateTime endDateTime, final String place) {
        this.moimId = moimId;
        this.agenda = agenda;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.place = place;
    }
}
