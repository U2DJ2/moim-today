package moim_today.global.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static moim_today.util.TestConstant.MESSAGE;
import static moim_today.util.TestConstant.STATUS_CODE;
import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @DisplayName("ErrorResponse에 올바른 Error 정보가 들어가는지 확인합니다.")
    @Test
    void ErrorResponse_테스트(){
        //given
        String statusCode = STATUS_CODE.value();
        String message = MESSAGE.value();

        //when
        ErrorResponse errorResponse = ErrorResponse.of(statusCode, message);

        //then
        assertThat(errorResponse.statusCode()).isEqualTo(statusCode);
        assertThat(errorResponse.message()).isEqualTo(message);
    }
}