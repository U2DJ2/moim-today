package booki_today.implement.mail;

import booki_today.dto.mail.MailSendRequest;
import booki_today.global.annotation.Implement;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Implement
public class MailSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final TemplateEngine htmlTemplateEngine;
    private final String from;

    public MailSender(
            final AmazonSimpleEmailService amazonSimpleEmailService,
            final TemplateEngine htmlTemplateEngine,
            @Value("${cloud.aws.ses.from}") final String from
    ) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.from = from;
    }

    public void send(final MailSendRequest mailSendRequest) {
        String subject = mailSendRequest.subject();
        Map<String, Object> variables = Map.of("data", mailSendRequest.content());
        List<String> to = mailSendRequest.to();

        try{
            String content = htmlTemplateEngine.process("authenticationMail.html", createContext(variables));
            SendEmailRequest sendEmailRequest = createSendEmailRequest(subject, content, to);
            amazonSimpleEmailService.sendEmail(sendEmailRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
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
