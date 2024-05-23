package moim_today.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.member.Member;
import moim_today.domain.schedule.AvailableTime;
import moim_today.domain.schedule.enums.AvailableColorHex;
import moim_today.dto.member.MemberSimpleResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static moim_today.global.constant.NumberConstant.CALENDAR_START_ID;

@Builder
public record AvailableTimeForMemberResponse(
        int calendarId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime,

        String colorHex
) {

    public static List<AvailableTimeForMemberResponse> from(final List<AvailableTime> availableTimes) {
        List<AvailableTimeForMemberResponse> availableTimeInMoimResponses = new ArrayList<>();
        int calendarId = CALENDAR_START_ID.value();

        for (AvailableTime availableTime : availableTimes) {
            calendarId++;
            List<Member> members = availableTime.members();
            AvailableTimeForMemberResponse availableTimeInMoimResponse = of(calendarId, availableTime, members);
            availableTimeInMoimResponses.add(availableTimeInMoimResponse);
        }

        return availableTimeInMoimResponses;
    }

    private static AvailableTimeForMemberResponse of(final int calendarId, final AvailableTime availableTime,
                                                     final List<Member> members) {
        return AvailableTimeForMemberResponse.builder()
                .calendarId(calendarId)
                .startDateTime(availableTime.startDateTime())
                .endDateTime(availableTime.endDateTime())
                .colorHex(AvailableColorHex.getHexByCount(members.size()))
                .build();
    }
}
