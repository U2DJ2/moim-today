package booki_today.application.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AmazonSMTPService implements SMTPService{
    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final TemplateEngine htmlTemplateEngine;
    private final String from;

    public AmazonSMTPService(
            final AmazonSimpleEmailService amazonSimpleEmailService,
            final TemplateEngine htmlTemplateEngine,
            @Value("${cloud.aws.ses.from}") final String from
    ) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.from = from;
    }

    @Override
    public void send(final String subject, Map<String, Object> variables, List<String> to) {
        String content = htmlTemplateEngine.process("index.html", createContext(variables));
        try{
            SendEmailRequest sendEmailRequest = createSendEmailRequest(subject, content, to);
            amazonSimpleEmailService.sendEmail(sendEmailRequest);
        }catch (Exception e){
            log.info("Email Failed");
            log.info("Error message: " + e.getMessage());
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
