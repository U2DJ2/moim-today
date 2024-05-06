package moim_today.dto.moim.moim;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

@Builder
public record MoimSimpleResponse(
        long moimId,
        String title,
        int capacity,
        int currentCount,
        String imageUrl,
        MoimCategory moimCategory,
        DisplayStatus displayStatus
) {

    @QueryProjection
    public MoimSimpleResponse {
    }

    public static MoimSimpleResponse from(final MoimJpaEntity moimJpaEntity) {
        return MoimSimpleResponse.builder()
                .moimId(moimJpaEntity.getId())
                .title(moimJpaEntity.getTitle())
                .capacity(moimJpaEntity.getCapacity())
                .currentCount(moimJpaEntity.getCurrentCount())
                .imageUrl(moimJpaEntity.getImageUrl())
                .moimCategory(moimJpaEntity.getMoimCategory())
                .displayStatus(moimJpaEntity.getDisplayStatus())
                .build();
    }
}
