package moim_today.domain.moim;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ViewedMoim(
        long moimId,
        LocalDateTime expiredTime
){

    public static ViewedMoim of(long moimId, LocalDateTime expiredTime) {
        return ViewedMoim.builder()
                .moimId(moimId)
                .expiredTime(expiredTime)
                .build();
    }

    public boolean isExpired() {
        return expiredTime.isBefore(LocalDateTime.now());
    }
}
