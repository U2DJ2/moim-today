package moim_today.dto.moim.moim;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record MoimMemberKickRequest(
        @Min(value = 1, message = MOIM_ID_MIN_ERROR) long moimId,
        @Min(value = 1, message = DELETE_MEMBER_ID_MIN_ERROR) long deleteMemberId
){
    private static final String MOIM_ID_MIN_ERROR = "잘못된 모임 ID 값이 입력 되었습니다.";
    private static final String DELETE_MEMBER_ID_MIN_ERROR = "잘못된 회원 ID 값이 입력 되었습니다.";
}
