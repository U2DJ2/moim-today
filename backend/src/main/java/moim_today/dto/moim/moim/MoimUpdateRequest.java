package moim_today.dto.moim.moim;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.moim.enums.MoimCategory;

import java.time.LocalDate;

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
        private static final String MOIM_ID_MIN_ERROR = "잘못된 모임 ID 값이 입력 되었습니다.";
        private static final String MOIM_TITLE_BLANK_ERROR = "모임 제목은 공백일 수 없습니다.";
        private static final String MOIM_CONTENT_BLANK_ERROR = "모임 내용은 공백일 수 없습니다.";
        private static final String MOIM_CAPACITY_MIN_ERROR = "모임 정원은 2명 이상이어야 합니다.";
        private static final String MOIM_CATEGORY_NULL_ERROR = "모임 카테고리는 필수 입력 항목입니다.";
        private static final String MOIM_START_DATE_NULL_ERROR = "모임 시간 일자는 필수 입력 항목입니다.";
        private static final String MOIM_END_DATE_NULL_ERROR = "모임 종료 일자 필수 입력 항목입니다.";
}
