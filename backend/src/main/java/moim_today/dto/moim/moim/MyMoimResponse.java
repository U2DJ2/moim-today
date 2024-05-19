package moim_today.dto.moim.moim;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record MyMoimResponse(
        long moimId,
        String title
) {

    @QueryProjection
    public MyMoimResponse {
    }

    public static MyMoimResponse of(final long moimId, final String title) {
        return MyMoimResponse.builder()
                .moimId(moimId)
                .title(title)
                .build();
    }
}
