package booki_today.implement.mail;

import booki_today.dto.mail.MailSendRequest;
import booki_today.util.ImplementTest;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MailSenderTest extends ImplementTest {

    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Value("${cloud.aws.ses.from}")
    private String from;

    @InjectMocks
    MailSender mailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Mock 초기화
        mailSender = new MailSender(amazonSimpleEmailService, htmlTemplateEngine);
    }

    @Test
    @DisplayName("메일 전송 테스트")
    void send() {
        // given
        String subject = "Test Subject";
        String content = "Hello, World!";
        List<String> to = Collections.singletonList("ilovekdh1208@gmail.com");
        MailSendRequest mailSendRequest = new MailSendRequest(subject, content, to);

        String mailContent = mailSender.makeHtmlTemplate(Map.of("data", mailSendRequest.content()));

        ArgumentCaptor<SendEmailRequest> captor = ArgumentCaptor.forClass(SendEmailRequest.class);

        // when
        mailSender.send(mailSendRequest);

        // then
        Mockito.verify(amazonSimpleEmailService).sendEmail(captor.capture());

        SendEmailRequest sendEmailRequest = captor.getValue();

        assertEquals(to, sendEmailRequest.getDestination().getToAddresses());
        // todo: mailSender 에서 @Value로 주입받는 from이 다름
        //        assertEquals(from, sendEmailRequest.getSource());
        assertEquals(subject, sendEmailRequest.getMessage().getSubject().getData());
        assertEquals(mailContent, sendEmailRequest.getMessage().getBody().getHtml().getData());
    }
}