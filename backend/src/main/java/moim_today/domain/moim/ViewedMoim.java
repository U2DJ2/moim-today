package moim_today.domain.moim;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public boolean isExpired() {
        return expiredTime.isBefore(LocalDateTime.now());
    }
}
