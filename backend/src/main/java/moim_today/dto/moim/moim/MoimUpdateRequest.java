package moim_today.dto.moim.moim;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;

import java.time.LocalDate;

@Builder
public record MoimUpdateRequest(
        long moimId,
        String title,
        String contents,
        int capacity,
        //imageUrl null 허용
        String imageUrl,
        //비밀번호 null 허용
        String password,
        MoimCategory moimCategory,
        DisplayStatus displayStatus,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate endDate
) {
}
