package moim_today.implement.mail;

import moim_today.dto.mail.MailSendRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static moim_today.global.constant.MailExceptionConstant.MAIL_SEND_ERROR;

@Slf4j
@Implement
public class MailSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    private final TemplateEngine htmlTemplateEngine;

    @Value("${cloud.aws.ses.from}")
    private String from;

    public MailSender(
            final AmazonSimpleEmailService amazonSimpleEmailService,
            final TemplateEngine htmlTemplateEngine
    ) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }

    public void send(final MailSendRequest mailSendRequest) {
        String subject = mailSendRequest.subject();
        Map<String, Object> variables = Map.of("data", mailSendRequest.content());
        List<String> to = mailSendRequest.to();

        try{
            String content = makeHtmlTemplate(variables);
            SendEmailRequest sendEmailRequest = createSendEmailRequest(subject, content, to);
            amazonSimpleEmailService.sendEmail(sendEmailRequest);
        }catch (Exception e){
            log.error("Exception [Err_Location]: {}", e.getStackTrace()[0]);
            throw new InternalServerException(MAIL_SEND_ERROR.message());
        }
    }

    public String makeHtmlTemplate(Map<String, Object> variables){
        return htmlTemplateEngine.process("authenticationMail.html", createContext(variables));
    }

    private Context createContext(Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);

        return context;
    }

    private SendEmailRequest createSendEmailRequest(final String subject, final String content, List<String> to) {
        return new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withSource(this.from)
                .withMessage(new Message()
                        .withSubject(createContent(subject))
                        .withBody(new Body().withHtml(createContent(content)))
                );
    }

    private Content createContent(String text){
        return new Content()
                .withCharset(StandardCharsets.UTF_8.name())
                .withData(text);
    }

}
