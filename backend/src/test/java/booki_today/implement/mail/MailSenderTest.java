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
    AmazonSimpleEmailService amazonSimpleEmailService;
    @Autowired
    TemplateEngine htmlTemplateEngine;
    @Value("${cloud.aws.ses.from}")
    String from;
    @InjectMocks
    MailSender mailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Mock 초기화
        mailSender = new MailSender(amazonSimpleEmailService, htmlTemplateEngine, from);
    }

    @Test
    @DisplayName("메일 전송 테스트")
    void send() {
        // 테스트할 메일 정보 생성
        String subject = "Test Subject";
        String content = "Hello, World!";
        List<String> to = Collections.singletonList("ilovekdh1208@gmail.com");
        MailSendRequest mailSendRequest = new MailSendRequest(subject, content, to);

        // 테스트 메일 본문 정보 생성
        String mailContent = mailSender.makeHtmlTemplate(Map.of("data", mailSendRequest.content()));

        // sendEmailRequest가 생성되었는지 확인하기 위한 ArgumentCaptor 생성
        ArgumentCaptor<SendEmailRequest> captor = ArgumentCaptor.forClass(SendEmailRequest.class);

        // send 메서드 호출
        mailSender.send(mailSendRequest);

        // sendEmail 메서드가 호출되었는지 검증
        Mockito.verify(amazonSimpleEmailService).sendEmail(captor.capture());

        // 생성된 SendEmailRequest 가져오기
        SendEmailRequest sendEmailRequest = captor.getValue();

        // sendEmailRequest의 내용 검증
        assertEquals(to, sendEmailRequest.getDestination().getToAddresses());
        assertEquals(from, sendEmailRequest.getSource());
        assertEquals(subject, sendEmailRequest.getMessage().getSubject().getData());
        assertEquals(mailContent, sendEmailRequest.getMessage().getBody().getHtml().getData());
    }
}