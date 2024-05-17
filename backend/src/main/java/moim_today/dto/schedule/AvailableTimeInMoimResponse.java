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
public record AvailableTimeInMoimResponse(
        int calendarId,
        List<MemberSimpleResponse> members,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime,

        String colorHex
) {

    public static List<AvailableTimeInMoimResponse> from(final List<AvailableTime> availableTimes) {
        List<AvailableTimeInMoimResponse> availableTimeInMoimResponses = new ArrayList<>();
        int calendarId = CALENDAR_START_ID.value();

        for (AvailableTime availableTime : availableTimes) {
            calendarId++;
            List<Member> members = availableTime.members();
            AvailableTimeInMoimResponse availableTimeInMoimResponse = of(calendarId, availableTime, members);
            availableTimeInMoimResponses.add(availableTimeInMoimResponse);
        }

        return availableTimeInMoimResponses;
    }

    private static AvailableTimeInMoimResponse of(final int calendarId, final AvailableTime availableTime,
                                                  final List<Member> members) {
        return AvailableTimeInMoimResponse.builder()
                .calendarId(calendarId)
                .members(MemberSimpleResponse.from(members))
                .startDateTime(availableTime.startDateTime())
                .endDateTime(availableTime.endDateTime())
                .colorHex(AvailableColorHex.getHexByCount(members.size()))
                .build();
    }
}
