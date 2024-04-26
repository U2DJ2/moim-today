package moim_today.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static moim_today.global.constant.MailConstant.*;
import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_MESSAGE;


// 이메일 인증시 타임리프로 만든 예외처리한 페이지를 보여줌
@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    // 400
    @ExceptionHandler(HandleExceptionPage.class)
    public String handleException(final HandleExceptionPage e, final Model model) {
        log.info("HandleExceptionPage={}", e.getPage());
        String certificationMessage = e.getPage();
        model.addAttribute(CERTIFICATION_MESSAGE.message(), certificationMessage);

        return CERTIFICATION_PAGE.value();
    }
}
