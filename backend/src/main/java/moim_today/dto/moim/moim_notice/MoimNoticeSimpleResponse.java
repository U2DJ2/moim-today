package moim_today.dto.moim.moim_notice;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Builder
public record MoimNoticeSimpleResponse(
        long moimNoticeId,
        String title,
        int year,
        int month,
        int day,
        String dayOfWeek
) {

        @QueryProjection
        public MoimNoticeSimpleResponse(final long moimNoticeId, final String title, final LocalDateTime createdAt) {
                this(moimNoticeId, title,
                        createdAt.getYear(),
                        createdAt.getMonthValue(),
                        createdAt.getDayOfMonth(),
                        createdAt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
                );
        }
}
