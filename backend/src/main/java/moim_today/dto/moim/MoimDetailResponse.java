package moim_today.dto.moim;

import lombok.Builder;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

import java.time.LocalDate;

@Builder
public record MoimDetailResponse(
        String title,
        String contents,
        int capacity,
        int currentCount,
        String imageUrl,
        MoimCategory moimCategory,
        DisplayStatus displayStatus,
        int views,
        LocalDate startDate,
        LocalDate endDate

) {

    public static MoimDetailResponse from(final MoimJpaEntity moimJpaEntity) {
        return MoimDetailResponse.builder()
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
