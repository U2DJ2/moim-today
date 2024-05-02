package moim_today.dto.moim.moim;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.persistence.entity.member.MemberJpaEntity;

import java.time.LocalDateTime;

@Builder
public record MoimMemberResponse(
        boolean isHost,
        long memberId,
        String memberName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
        LocalDateTime joinedDate
) {

    public static MoimMemberResponse of(final MemberJpaEntity memberJpaEntity, final boolean isHost,
                                        final LocalDateTime joinedDate) {
        return MoimMemberResponse.builder()
                .isHost(isHost)
                .memberId(memberJpaEntity.getId())
                .memberName(memberJpaEntity.getUsername())
                .joinedDate(joinedDate)
                .build();
    }
}
