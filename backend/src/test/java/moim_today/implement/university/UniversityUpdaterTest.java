package moim_today.implement.university;

import moim_today.domain.university.ExtractUniversity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.NumberConstant.ADMIN_PASSWORD_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

class UniversityUpdaterTest extends ImplementTest {

    @Autowired
    private UniversityUpdater universityUpdater;

    private final String AJOU_UNIV_NAME = "아주대학교";

    private final String AJOU_EMAIL = "ajou.ac.kr";

    @DisplayName("같은 이름으로 저장된 대학이 있을 때, 비어있는 이메일만 업데이트한다")
    @Test
    void 중복되는_대학이름이_있을_때_putUniversity() {
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(AJOU_UNIV_NAME)
                .universityEmail("")
                .build();

        universityRepository.save(universityJpaEntity);

        ExtractUniversity extractUniversity = ExtractUniversity.builder()
                .schoolName(AJOU_UNIV_NAME)
                .link(AJOU_EMAIL)
                .build();

        // when
        universityUpdater.putUniversity(extractUniversity);
        UniversityJpaEntity findUniversity = universityRepository.findByName(AJOU_UNIV_NAME).get();

        // then
        assertThat(findUniversity.getUniversityEmail()).isEqualTo(AJOU_EMAIL);
    }

    @DisplayName("같은 이름으로 저장된 대학이 있을 때, 관리자 비밀번호를 같이 저장한다.")
    @Test
    void 중복되는_대학이름이_있을_때_putUniversityWithAdminPassword() {
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(AJOU_UNIV_NAME)
                .universityEmail("")
                .build();

        universityRepository.save(universityJpaEntity);

        ExtractUniversity extractUniversity = ExtractUniversity.builder()
                .schoolName(AJOU_UNIV_NAME)
                .link(AJOU_EMAIL)
                .build();

        // when
        universityUpdater.putUniversity(extractUniversity);
        UniversityJpaEntity findUniversity = universityRepository.findByName(AJOU_UNIV_NAME).get();

        // then
        assertThat(findUniversity.getAdminPassword()).isNotNull();
        assertThat(findUniversity.getAdminPassword().length()).isEqualTo(ADMIN_PASSWORD_LENGTH.value());
    }

    @DisplayName("같은 이름으로 저장된 대학이 없을 때, 대학 정보를 저장한다")
    @Test
    void 중복되는_대학이름이_없을_때_putUniversity(){
        // given
        ExtractUniversity extractUniversity = ExtractUniversity.builder()
                .schoolName(AJOU_UNIV_NAME)
                .link(AJOU_EMAIL)
                .build();

        // when
        universityUpdater.putUniversity(extractUniversity);
        UniversityJpaEntity findUniversity = universityRepository.findByName(AJOU_UNIV_NAME).get();

        // then
        assertThat(findUniversity.getUniversityEmail()).isEqualTo(AJOU_EMAIL);
    }

    @DisplayName("같은 이름으로 저장된 대학이 없을 때, 관리자 비밀번호를 같이 저장한다.")
    @Test
    void 중복되는_대학이름이_없을_때_putUniversityWithAdminPassword(){
        // given
        ExtractUniversity extractUniversity = ExtractUniversity.builder()
                .schoolName(AJOU_UNIV_NAME)
                .link(AJOU_EMAIL)
                .build();

        // when
        universityUpdater.putUniversity(extractUniversity);
        UniversityJpaEntity findUniversity = universityRepository.findByName(AJOU_UNIV_NAME).get();

        // then
        assertThat(findUniversity.getAdminPassword()).isNotNull();
        assertThat(findUniversity.getAdminPassword().length()).isEqualTo(ADMIN_PASSWORD_LENGTH.value());
    }
}