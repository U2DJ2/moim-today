package moim_today.dto.moim.moim;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record MoimJoinRequest(
        @Min(value = 1, message = MOIM_ID_MIN_ERROR) long moimId
) {
    private static final String MOIM_ID_MIN_ERROR = "잘못된 모임 ID 값이 입력 되었습니다.";
}
