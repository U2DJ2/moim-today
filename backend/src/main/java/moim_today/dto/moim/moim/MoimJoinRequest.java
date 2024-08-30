package moim_today.dto.moim.moim;

import jakarta.validation.constraints.Min;
import lombok.Builder;

import static moim_today.global.constant.exception.ValidationExceptionConstant.MOIM_ID_MIN_ERROR;

@Builder
public record MoimJoinRequest(
        @Min(value = 0, message = MOIM_ID_MIN_ERROR) long moimId
) {

}
