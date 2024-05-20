package moim_today.presentation.email_subscribe;

import moim_today.application.email_subscribe.EmailSubscribeService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.mail.EmailSubscribeRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class EmailSubscribeController {

    private final EmailSubscribeService emailSubscribeService;

    public EmailSubscribeController(final EmailSubscribeService emailSubscribeService) {
        this.emailSubscribeService = emailSubscribeService;
    }

    @PostMapping("/email-subscription")
    public void subscribeEmail(@Login final MemberSession memberSession,
                               @RequestBody final EmailSubscribeRequest emailSubscribeRequest) {
        emailSubscribeService.updateSubscribeStatus(memberSession.id(), emailSubscribeRequest.subscribeStatus());
    }
}
