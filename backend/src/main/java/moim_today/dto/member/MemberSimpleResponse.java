package moim_today.dto.member;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import moim_today.domain.member.Member;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Builder
public record MemberSimpleResponse(
        long memberId,
        String username,
        String memberProfileImageUrl
) {

    public static MemberSimpleResponse from(final Member member) {
        return MemberSimpleResponse.builder()
                .memberId(member.memberId())
                .username(member.username())
                .memberProfileImageUrl(member.memberProfileImageUrl())
                .build();
    }

    public static List<MemberSimpleResponse> from(final List<Member> members) {
        return members.stream()
                .map(MemberSimpleResponse::from)
                .collect(toList());
    }

    @QueryProjection
    public MemberSimpleResponse {
    }
}
