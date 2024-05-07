package moim_today.persistence.entity.university;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UniversityJpaEntityTest {

    @DisplayName("입력받은 이메일로 수정한다")
    @Test
    void updateEmail() {
        // given
        UniversityJpaEntity ajouUniv = UniversityJpaEntity.builder()
                .universityName("아주대학교")
                .universityEmail("ac.kr")
                .build();
        // when
        ajouUniv.updateEmail("ajou.ac.kr");

        // then
        assertThat(ajouUniv.getUniversityEmail()).isEqualTo("ajou.ac.kr");
    }
}