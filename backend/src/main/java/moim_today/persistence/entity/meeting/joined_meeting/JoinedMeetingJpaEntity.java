package moim_today.persistence.entity.meeting.joined_meeting;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

@Getter
@Table(name = "joined_meeting")
@Entity
public class JoinedMeetingJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joined_meeting_id")
    private long id;

    @Association
    private long meetingId;

    @Association
    private long memberId;

    private boolean Attendance;

    protected JoinedMeetingJpaEntity() {
    }

    @Builder
    private JoinedMeetingJpaEntity(final long meetingId, final long memberId, final boolean attendance) {
        this.meetingId = meetingId;
        this.memberId = memberId;
        Attendance = attendance;
    }
}
