package moim_today.presentation.certification_token;

import moim_today.application.certification_token.CertificationTokenService;
import moim_today.dto.certification_token.PasswordFindRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/certification-token")
@RestController
public class CertificationTokenController {

    private final CertificationTokenService certificationTokenService;

    public CertificationTokenController(final CertificationTokenService certificationTokenService) {
        this.certificationTokenService = certificationTokenService;
    }

    @PostMapping("/password")
    public void createPasswordToken(@RequestBody final PasswordFindRequest passwordFindRequest) {
        certificationTokenService.createPasswordToken(passwordFindRequest.email());
    }
}
