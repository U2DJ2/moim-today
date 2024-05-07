package moim_today.dto.moim.moim_notice;

import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;

public record MoimNoticeCreateRequest(
        long moimId,
        String title,
        String contents
) {

    public MoimNoticeJpaEntity toEntity() {
        return MoimNoticeJpaEntity.builder()
                .moimId(moimId)
                .title(title)
                .contents(contents)
                .build();
    }
}
