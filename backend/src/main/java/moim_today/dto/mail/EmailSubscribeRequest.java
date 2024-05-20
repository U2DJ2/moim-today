package moim_today.dto.mail;

import lombok.Builder;

@Builder
public record EmailSubscribeRequest(
        boolean subscribeStatus
) {

    public static EmailSubscribeRequest of(final boolean subscribeStatus) {
        return EmailSubscribeRequest.builder()
                .subscribeStatus(subscribeStatus)
                .build();
    }
}
