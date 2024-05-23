package moim_today.dto.mail;

import lombok.Builder;

@Builder
public record EmailSubscriptionResponse(
        boolean subscribeStatus
) {

    public static EmailSubscriptionResponse of(final boolean subscribeStatus) {
        return EmailSubscriptionResponse.builder()
                .subscribeStatus(subscribeStatus)
                .build();
    }
}
