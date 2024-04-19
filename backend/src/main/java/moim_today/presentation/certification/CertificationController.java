package moim_today.presentation.certification;

import moim_today.application.certification.CertificationService;
import moim_today.dto.certification_token.EmailCertificationRequest;
import moim_today.dto.certification_token.PasswordFindRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/certification")
@Controller
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(final CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @ResponseBody
    @PostMapping("/password")
    public void createPasswordToken(@RequestBody final PasswordFindRequest passwordFindRequest) {
        certificationService.sendPasswordToken(passwordFindRequest.email());
    }

    @ResponseBody
    @PostMapping("/email")
    public void createCertificationEmail(@RequestBody final EmailCertificationRequest emailCertificationRequest) {
        certificationService.sendCertificationEmail(emailCertificationRequest.email());
    }

    @GetMapping("/email/{certificationToken}")
    public String certifyEmail(@PathVariable final String certificationToken) {
        certificationService.certifyEmail(certificationToken);
        return "emailCertificationComplete";
    }
}
