package moim_today.fake_class.meeting.meeting;

import moim_today.application.meeting.meeting.MeetingService;
import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.MeetingSimpleResponse;
import moim_today.dto.member.MemberSimpleResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.util.TestConstant.*;

public class FakeMeetingService implements MeetingService {

    @Override
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {

    }

    @Override
    public List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final long memberId,
                                                       final MeetingStatus meetingStatus) {
        MeetingSimpleResponse meetingSimpleResponse1 = MeetingSimpleResponse.builder()
                .meetingId(MEETING_ID.longValue())
                .agenda(MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 4))
                .dayOfWeek(DayOfWeek.MONDAY)
                .dDay(3)
                .build();

        MeetingSimpleResponse meetingSimpleResponse2 = MeetingSimpleResponse.builder()
                .meetingId(MEETING_ID.longValue() + 1)
                .agenda(MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 5))
                .dayOfWeek(DayOfWeek.TUESDAY)
                .dDay(2)
                .build();

        MeetingSimpleResponse meetingSimpleResponse3 = MeetingSimpleResponse.builder()
                .meetingId(MEETING_ID.longValue() + 2)
                .agenda(MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 6))
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .dDay(1)
                .build();

        return List.of(meetingSimpleResponse1, meetingSimpleResponse2, meetingSimpleResponse3);
    }

    @Override
    public MeetingDetailResponse findDetailsById(final long meetingId) {
        MemberSimpleResponse memberSimpleResponse1 = MemberSimpleResponse.builder()
                .memberId(MEMBER_ID.longValue())
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        MemberSimpleResponse memberSimpleResponse2 = MemberSimpleResponse.builder()
                .memberId(MEMBER_ID.longValue())
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        MemberSimpleResponse memberSimpleResponse3 = MemberSimpleResponse.builder()
                .memberId(MEMBER_ID.longValue())
                .username(USERNAME.value())
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        return MeetingDetailResponse.builder()
                .meetingId(MEMBER_ID.longValue())
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .members(List.of(memberSimpleResponse1, memberSimpleResponse2, memberSimpleResponse3))
                .build();
    }
}
