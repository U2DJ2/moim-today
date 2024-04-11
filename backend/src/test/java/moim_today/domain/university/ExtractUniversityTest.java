package moim_today.domain.university;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExtractUniversityTest {

    @DisplayName("학교 홈페이지 링크에서 학교 이메일 확장자를 추출하는 테스트")
    @Test
    void extractEmailExtension() {
        // given
        ExtractUniversity extractUniversity = ExtractUniversity.builder()
                .link("http://www.ajou.ac.kr.com")
                .schoolName("ajou")
                .schoolType("대학교")
                .build();

        // when
        extractUniversity.extractEmailExtension();

        // then
        assertThat("ajou.ac.kr").isEqualTo(extractUniversity.getLink());
    }

    @DisplayName("학교 타입이 전문학교 또는 대학교인지 검사 통과한 경우")
    @Test
    void checkUniversityTypeSuccess() {
        // given
        ExtractUniversity extractUniversity = ExtractUniversity.builder()
                .link("http://www.ajou.ac.kr.com")
                .schoolName("ajou")
                .schoolType("대학교")
                .build();

        // when
        boolean result = extractUniversity.checkUniversityType();

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("학교 타입이 전문학교 또는 대학교 아닌 경우")
    @Test
    void checkUniversityTypeFail(){
        // given
        ExtractUniversity extractUniversity = ExtractUniversity.builder()
                .link("http://www.ajou.ac.kr.com")
                .schoolName("ajou")
                .schoolType("사내 대학교")
                .build();

        // when
        boolean result = extractUniversity.checkUniversityType();

        // then
        assertThat(result).isFalse();
    }
}