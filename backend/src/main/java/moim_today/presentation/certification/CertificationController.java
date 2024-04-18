package moim_today.presentation.certification;

import moim_today.application.certification.CertificationService;
import moim_today.dto.certification_token.EmailCertificationRequest;
import moim_today.dto.certification_token.PasswordFindRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/certification")
@RestController
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(final CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping("/password")
    public void createPasswordToken(@RequestBody final PasswordFindRequest passwordFindRequest) {
        certificationService.createPasswordToken(passwordFindRequest.email());
    }

    @PostMapping("/email")
    public void certifyEmail(@RequestBody final EmailCertificationRequest emailCertificationRequest) {
        certificationService.certifyEmail(emailCertificationRequest.email());
    }
}
