package moim_today.dto.moim;

import lombok.Builder;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity.MoimJpaEntityBuilder;

import java.time.LocalDate;

import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_IMAGE_URL;
import static moim_today.global.constant.MoimConstant.DEFAULT_MOIM_PASSWORD;
import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_CURRENT_COUNT;
import static moim_today.global.constant.NumberConstant.DEFAULT_MOIM_VIEWS;

@Builder
public record PublicMoimAppendRequest(
        String title,
        String contents,
        int capacity,
        String imageUrl,
        MoimCategory moimCategory,
        LocalDate startDate,
        LocalDate endDate
) {

    public MoimJpaEntity toEntity(final long memberId, final long universityId) {

        MoimJpaEntityBuilder moimJpaEntityBuilder = MoimJpaEntity.builder()
                .universityId(universityId)
                .memberId(memberId)
                .title(title)
                .contents(contents)
                .capacity(capacity)
                .currentCount(DEFAULT_MOIM_CURRENT_COUNT.value())
                .password(DEFAULT_MOIM_PASSWORD.value())
                .moimCategory(moimCategory)
                .displayStatus(DisplayStatus.PUBLIC)
                .views(DEFAULT_MOIM_VIEWS.value())
                .startDate(startDate)
                .endDate(endDate);

        if (imageUrl == null) {
            return buildMoimByImageUrl(moimJpaEntityBuilder, DEFAULT_MOIM_IMAGE_URL.value());
        } else {
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
