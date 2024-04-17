package moim_today.domain.email;

import lombok.Builder;

@Builder
public record EmailValidator (
    String email
) {

}