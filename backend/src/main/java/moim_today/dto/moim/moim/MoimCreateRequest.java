package moim_today.dto.moim.moim;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity.MoimJpaEntityBuilder;

import java.time.LocalDate;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_CURRENT_COUNT;
import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_VIEWS;
import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record MoimCreateRequest(
        @NotBlank(message = MOIM_TITLE_BLANK_ERROR) String title,
        @NotBlank(message = MOIM_CONTENT_BLANK_ERROR) String contents,
        @Min(value = 2, message = MOIM_CAPACITY_MIN_ERROR) int capacity,
        String imageUrl,
        @NotNull(message = MOIM_CATEGORY_NULL_ERROR) MoimCategory moimCategory,
        @NotNull(message = MOIM_START_DATE_NULL_ERROR) LocalDate startDate,
        @NotNull(message = MOIM_END_DATE_NULL_ERROR) LocalDate endDate
) {
    public MoimJpaEntity toEntity(final long memberId, final long universityId) {

        MoimJpaEntityBuilder moimJpaEntityBuilder = MoimJpaEntity.builder()
                .universityId(universityId)
                .memberId(memberId)
                .title(title)
                .contents(contents)
                .capacity(capacity)
                .currentCount(DEFAULT_MOIM_CURRENT_COUNT.value())
                .moimCategory(moimCategory)
                .views(DEFAULT_MOIM_VIEWS.value())
                .startDate(startDate)
                .endDate(endDate);

        if (imageUrl == null || imageUrl.isBlank()) {
            return buildMoimByImageUrl(moimJpaEntityBuilder, DEFAULT_MOIM_IMAGE_URL.value());
        } else{
            return buildMoimByImageUrl(moimJpaEntityBuilder, imageUrl);
        }
    }

    private MoimJpaEntity buildMoimByImageUrl(final MoimJpaEntityBuilder moimJpaEntityBuilder,
                                              final String imageUrl) {
        return moimJpaEntityBuilder
                .imageUrl(imageUrl)
                .build();
    }
}
