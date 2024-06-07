package moim_today.dto.moim.moim;

import jakarta.validation.constraints.Min;

import static moim_today.global.constant.exception.ValidationExceptionConstant.MOIM_ID_MIN_ERROR;

public record MoimDeleteRequest(
        @Min(value = 0, message = MOIM_ID_MIN_ERROR) long moimId
) {

}
