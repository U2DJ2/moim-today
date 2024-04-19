package moim_today.presentation.certification;

import moim_today.application.certification.email.EmailCertificationService;
import moim_today.application.certification.password.PasswordCertificationService;
import moim_today.dto.certification.CompleteEmailCertificationRequest;
import moim_today.dto.certification.CompleteEmailCertificationResponse;
import moim_today.dto.certification.EmailCertificationRequest;
import moim_today.dto.certification.PasswordFindRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static moim_today.global.constant.MailConstant.EMAIL_CERTIFICATION_COMPLETE;

@RequestMapping("/api/certification")
@Controller
public class CertificationController {

    private final PasswordCertificationService passwordCertificationService;
    private final EmailCertificationService emailCertificationService;

    public CertificationController(final PasswordCertificationService passwordCertificationService,
                                   final EmailCertificationService emailCertificationService) {
        this.passwordCertificationService = passwordCertificationService;
        this.emailCertificationService = emailCertificationService;
    }

    @ResponseBody
    @PostMapping("/password")
    public void sendPasswordToken(@RequestBody final PasswordFindRequest passwordFindRequest) {
        passwordCertificationService.sendPasswordToken(passwordFindRequest.email());
    }

    @ResponseBody
    @PostMapping("/email")
    public void sendCertificationEmail(@RequestBody final EmailCertificationRequest emailCertificationRequest) {
        emailCertificationService.sendCertificationEmail(emailCertificationRequest.email());
    }

    @ResponseBody
    @PostMapping("/email/complete")
    public CompleteEmailCertificationResponse emailCertificationComplete(
            @RequestBody final CompleteEmailCertificationRequest completeEmailCertificationRequest) {
        return emailCertificationService.completeCertification(completeEmailCertificationRequest.email());
    }

    @GetMapping("/email/{certificationToken}")
    public String certifyEmail(@PathVariable final String certificationToken) {
        emailCertificationService.certifyEmail(certificationToken);
        return EMAIL_CERTIFICATION_COMPLETE.value();
    }
}
