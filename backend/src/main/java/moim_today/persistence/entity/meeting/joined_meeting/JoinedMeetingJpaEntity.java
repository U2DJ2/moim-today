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

    private boolean attendance;

    private boolean invitationSent;

    protected JoinedMeetingJpaEntity() {
    }

    @Builder
    private JoinedMeetingJpaEntity(final long meetingId, final long memberId,
                                   final boolean attendance, final boolean invitationSent) {
        this.meetingId = meetingId;
        this.memberId = memberId;
        this.attendance = attendance;
        this.invitationSent = invitationSent;
    }

    public static JoinedMeetingJpaEntity toEntity(final long meetingId, final long memberId,
                                                  final boolean attendance) {
        return JoinedMeetingJpaEntity.builder()
                .meetingId(meetingId)
                .memberId(memberId)
                .attendance(attendance)
                .invitationSent(false)
                .build();
    }

    public void updateAttendance(final boolean attendance) {
        this.attendance = attendance;
    }
}
