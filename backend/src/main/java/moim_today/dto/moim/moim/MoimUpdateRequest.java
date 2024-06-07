package moim_today.dto.moim.moim;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.moim.enums.MoimCategory;

import java.time.LocalDate;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record MoimUpdateRequest(
        @Min(value = 0, message = MOIM_ID_MIN_ERROR) long moimId,
        @NotBlank(message = MOIM_TITLE_BLANK_ERROR) String title,
        @NotBlank(message = MOIM_CONTENT_BLANK_ERROR) String contents,
        @Min(value = 2, message = MOIM_CAPACITY_MIN_ERROR) int capacity,
        String imageUrl,
        @NotNull(message = MOIM_CATEGORY_NULL_ERROR) MoimCategory moimCategory,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @NotNull(message = MOIM_START_DATE_NULL_ERROR) LocalDate startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @NotNull(message = MOIM_END_DATE_NULL_ERROR) LocalDate endDate
) {

}
