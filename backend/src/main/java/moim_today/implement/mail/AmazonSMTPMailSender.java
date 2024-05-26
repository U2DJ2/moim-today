package moim_today.implement.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.slf4j.Slf4j;
import moim_today.dto.mail.MailSendRequest;
import moim_today.global.error.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static moim_today.global.constant.MailConstant.DATA;
import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_SEND_ERROR;

@Slf4j
public class AmazonSMTPMailSender implements SMTPMailSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    private final TemplateEngine htmlTemplateEngine;

    @Value("${cloud.aws.ses.from}")
    private String from;

    public AmazonSMTPMailSender(
            final AmazonSimpleEmailService amazonSimpleEmailService,
            final TemplateEngine htmlTemplateEngine
    ) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }

    public void send(final MailSendRequest mailSendRequest, final String contentTemplate, final String data) {
        String subject = mailSendRequest.subject();
        Map<String, Object> variables = Map.of(DATA.value(), data);
        List<String> to = mailSendRequest.to();

        try{
            String content = makeHtmlTemplate(variables, contentTemplate);
            SendEmailRequest sendEmailRequest = createSendEmailRequest(subject, content, to);
            amazonSimpleEmailService.sendEmail(sendEmailRequest);
        }catch (Exception e){
            log.error("Exception [Err_Location]: {}", e.getStackTrace()[0]);
            throw new InternalServerException(MAIL_SEND_ERROR.message());
        }
    }

    private String makeHtmlTemplate(final Map<String, Object> variables, final String contentTemplate){
        return htmlTemplateEngine.process(contentTemplate, createContext(variables));
    }

    private Context createContext(final Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);

        return context;
    }

    private SendEmailRequest createSendEmailRequest(final String subject, final String content, final List<String> to) {
        return new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withSource(this.from)
                .withMessage(new Message()
                        .withSubject(createContent(subject))
                        .withBody(new Body().withHtml(createContent(content)))
                );
    }

    private Content createContent(final String text){
        return new Content()
                .withCharset(StandardCharsets.UTF_8.name())
                .withData(text);
    }
}
