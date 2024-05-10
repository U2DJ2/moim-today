package moim_today.fake_class.meeting;

import moim_today.application.meeting.MeetingService;
import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.dto.meeting.MeetingDetailResponse;
import moim_today.dto.meeting.MeetingSimpleResponse;
import moim_today.util.TestConstant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class FakeMeetingService implements MeetingService {

    @Override
    public void createMeeting(final MeetingCreateRequest meetingCreateRequest) {

    }

    @Override
    public List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final MeetingStatus meetingStatus) {
        MeetingSimpleResponse meetingSimpleResponse1 = MeetingSimpleResponse.builder()
                .meetingId(TestConstant.MEETING_ID.longValue())
                .agenda(TestConstant.MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 4))
                .dayOfWeek(DayOfWeek.MONDAY)
                .dDay(3)
                .build();

        MeetingSimpleResponse meetingSimpleResponse2 = MeetingSimpleResponse.builder()
                .meetingId(TestConstant.MEETING_ID.longValue() + 1)
                .agenda(TestConstant.MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 5))
                .dayOfWeek(DayOfWeek.TUESDAY)
                .dDay(2)
                .build();

        MeetingSimpleResponse meetingSimpleResponse3 = MeetingSimpleResponse.builder()
                .meetingId(TestConstant.MEETING_ID.longValue() + 2)
                .agenda(TestConstant.MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 6))
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .dDay(1)
                .build();

        return List.of(meetingSimpleResponse1, meetingSimpleResponse2, meetingSimpleResponse3);
    }

    @Override
    public MeetingDetailResponse findDetailsById(final long moimId) {
        return null;
    }
}
