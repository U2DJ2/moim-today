package moim_today.fake_class.meeting.meeting;

import moim_today.application.meeting.meeting.MeetingService;
import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.meeting.*;
import moim_today.dto.member.MemberSimpleResponse;
import moim_today.global.error.ForbiddenException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_FORBIDDEN_ERROR;
import static moim_today.util.TestConstant.*;

public class FakeMeetingService implements MeetingService {

    @Override
    public MeetingCreateResponse createMeeting(final long memberId, final MeetingCreateRequest meetingCreateRequest) {
        return MeetingCreateResponse.of(MEETING_ID.longValue(), meetingCreateRequest);
    }

    @Override
    public List<MeetingSimpleResponse> findAllByMoimId(final long moimId, final long memberId,
                                                       final MeetingStatus meetingStatus,
                                                       final LocalDateTime lastStartDateTime) {
        MeetingSimpleResponse meetingSimpleResponse1 = MeetingSimpleResponse.builder()
                .meetingId(MEETING_ID.longValue())
                .agenda(MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 4))
                .dDay(3)
                .build();

        MeetingSimpleResponse meetingSimpleResponse2 = MeetingSimpleResponse.builder()
                .meetingId(MEETING_ID.longValue() + 1)
                .agenda(MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 5))
                .dDay(2)
                .build();

        MeetingSimpleResponse meetingSimpleResponse3 = MeetingSimpleResponse.builder()
                .meetingId(MEETING_ID.longValue() + 2)
                .agenda(MEETING_AGENDA.value())
                .startDate(LocalDate.of(2024, 3, 6))
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

    @Override
    public void updateMeeting(final long memberId, final MeetingUpdateRequest meetingUpdateRequest) {
        if (meetingUpdateRequest.meetingId() != MEETING_ID.longValue()) {
            throw new ForbiddenException(MEETING_FORBIDDEN_ERROR.message());
        }
    }

    @Override
    public void deleteMeeting(final long memberId, final long meetingId) {
        if (meetingId != MEETING_ID.longValue()) {
            throw new ForbiddenException(MEETING_FORBIDDEN_ERROR.message());
        }
    }
}
