package moim_today.implement.mail;

import com.amazonaws.services.simpleemail.model.Content;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import moim_today.dto.mail.MailSendRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static moim_today.global.constant.MailConstant.DATA;
import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_SEND_ERROR;

@Slf4j
@Implement
public class NaverSMTPMailSender implements SMTPMailSender {

    private final TemplateEngine htmlTemplateEngine;
    private final JavaMailSender emailSender;

    @Value("${send.mail.from}")
    private String from;

    public NaverSMTPMailSender(final TemplateEngine htmlTemplateEngine,
                               final JavaMailSender emailSender) {
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.emailSender = emailSender;
    }

    @Override
    public void send(final MailSendRequest mailSendRequest, final String contentTemplate, final String data) {
        String subject = mailSendRequest.subject();
        Map<String, Object> variables = Map.of(DATA.value(), data);
        List<String> to = mailSendRequest.to();

        try{
            String content = makeHtmlTemplate(variables, contentTemplate);
            for(String eachTo : to){
                MimeMessage emailForm = createEmailForm(content, subject, eachTo);
                emailSender.send(emailForm);
            }
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

    public MimeMessage createEmailForm(final String content, final String subject, final String to) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        message.setFrom(from);

        return message;
    }

    private Content createContent(final String text){
        return new Content()
                .withCharset(StandardCharsets.UTF_8.name())
                .withData(text);
    }
}
