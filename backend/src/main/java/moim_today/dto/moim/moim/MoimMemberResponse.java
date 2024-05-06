package moim_today.dto.moim.moim;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MoimMemberResponse(
        boolean isHost,
        long memberId,
        String memberName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime joinedDate
) {

    @QueryProjection
    public MoimMemberResponse{

    }
}
