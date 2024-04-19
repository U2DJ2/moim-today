package moim_today.implement.university;

import moim_today.dto.university.UniversityInfoResponse;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static moim_today.util.TestConstant.EMAIL;
import static moim_today.util.TestConstant.UNIVERSITY_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class UniversityFinderTest extends ImplementTest {

    @Autowired
    private UniversityFinder universityFinder;

    @DisplayName("모든 대학교의 정보를 가져오는 테스트")
    @Test
    void getAllUniversity() {
        // given
        List<UniversityInfoResponse> actualUnivs = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                    .universityName(UNIVERSITY_NAME.value()+i)
                    .universityEmail(i+EMAIL.value())
                    .build();
            universityRepository.save(universityJpaEntity);
            actualUnivs.add(UniversityInfoResponse.of(universityJpaEntity));
        }

        // when
        List<UniversityInfoResponse> getAllUniv = universityFinder.getAllUniversity();

        // then
        assertThat(getAllUniv).isEqualTo(actualUnivs);
    }

    @DisplayName("대학교 이름으로 찾아서 Optional 로 반환한다")
    @Test
    void findByName() {
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIVERSITY_NAME.value())
                .universityEmail(EMAIL.value())
                .build();

        universityRepository.save(universityJpaEntity);

        // when
        Optional<UniversityJpaEntity> findUniversity = universityFinder.findByName(UNIVERSITY_NAME.value());

        // then
        assertThat(findUniversity.get()).isInstanceOf(UniversityJpaEntity.class);
    }

    @DisplayName("대학교 이름이 없을 때 테스트")
    @Test
    void 대학교_이름으로_찾았는데_대학교가_없을_때() {
        // when
        Optional<UniversityJpaEntity> findUniversity = universityRepository.findByName(UNIVERSITY_NAME.value());

        // then
        assertThat(findUniversity.isEmpty()).isTrue();
    }

    @DisplayName("대학교 ID가 있는지 없는지 검사한다")
    @Test
    void 대학교_ID가_있는지_검사() {
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIVERSITY_NAME.value())
                .universityEmail(EMAIL.value())
                .build();
        UniversityJpaEntity savedUniversity = universityRepository.save(universityJpaEntity);

        // expected
        assertThat(universityFinder.checkUniversityIdIsPresent(savedUniversity.getId())).isTrue();
    }
}