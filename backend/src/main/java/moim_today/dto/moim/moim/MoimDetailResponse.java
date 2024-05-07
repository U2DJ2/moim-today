package moim_today.dto.moim.moim;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

import java.time.LocalDate;

@Builder
public record MoimDetailResponse(
        long moimId,
        String title,
        String contents,
        int capacity,
        int currentCount,
        String imageUrl,
        MoimCategory moimCategory,
        DisplayStatus displayStatus,
        int views,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate endDate

) {

    public static MoimDetailResponse from(final MoimJpaEntity moimJpaEntity) {
        return MoimDetailResponse.builder()
                .moimId(moimJpaEntity.getId())
                .title(moimJpaEntity.getTitle())
                .contents(moimJpaEntity.getContents())
                .capacity(moimJpaEntity.getCapacity())
                .currentCount(moimJpaEntity.getCurrentCount())
                .imageUrl(moimJpaEntity.getImageUrl())
                .moimCategory(moimJpaEntity.getMoimCategory())
                .displayStatus(moimJpaEntity.getDisplayStatus())
                .views(moimJpaEntity.getViews())
                .startDate(moimJpaEntity.getStartDate())
                .endDate(moimJpaEntity.getEndDate())
                .build();
    }
}
