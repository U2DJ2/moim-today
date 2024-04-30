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
public record MoimAppendRequest(
        String title,
        String contents,
        int capacity,
        //비밀번호 null 허용
        String password,
        //모임 사진 null 허용
        String imageUrl,
        MoimCategory moimCategory,
        DisplayStatus displayStatus,
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
                .moimCategory(moimCategory)
                .displayStatus(displayStatus)
                .views(DEFAULT_MOIM_VIEWS.value())
                .startDate(startDate)
                .endDate(endDate);

        if (displayStatus.equals(DisplayStatus.PRIVATE) && password != null) {
            moimJpaEntityBuilder.password(password);
        } else {
            moimJpaEntityBuilder.password(DEFAULT_MOIM_PASSWORD.value());
        }

        if (imageUrl == null) {
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
