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
import java.util.Random;

import static moim_today.util.TestConstant.*;
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
    void noUniversityFoundByUniversityNameTest() {
        // when
        Optional<UniversityJpaEntity> findUniversity = universityFinder.findByName(UNIVERSITY_NAME.value());

        // then
        assertThat(findUniversity.isEmpty()).isTrue();
    }

    @DisplayName("대학교 테이블에 존재하는 대학교 이름만 반환한다")
    @Test
    void findUniversitiesByUniversityNames(){
        // given
        final int MAX_UNIV = 5;
        final int MAX_TEST_UNIV = 10;
        Random random = new Random();
        List<String> universityNames = new ArrayList<>();
        String testUniversityName = UNIVERSITY_NAME.value();

        for(int i = 0; i < MAX_UNIV; i++){
            UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                    .universityName(testUniversityName+i)
                    .universityEmail(AJOU_EMAIL.value())
                    .build();
            universityRepository.save(universityJpaEntity);

            universityNames.add(testUniversityName+i);
        }

        for(int i = MAX_UNIV; i < MAX_TEST_UNIV; i++){
            universityNames.add(testUniversityName+i);
        }

        // when
        List<UniversityJpaEntity> findUniversities = universityFinder.findUniversitiesByName(universityNames);

        // then
        UniversityJpaEntity randomUniversity = UniversityJpaEntity.builder()
                .universityName(testUniversityName+(random.nextInt(MAX_UNIV)))
                .universityEmail(AJOU_EMAIL.value())
                .build();
        assertThat(findUniversities.size()).isEqualTo(MAX_UNIV);
        assertThat(findUniversities.stream().filter(u -> u.getUniversityName().equals(randomUniversity.getUniversityName()))
                .findAny())
                .isPresent();
    }

    @DisplayName("대학교 ID가 있는지 없는지 검사한다")
    @Test
    void validateUniversityIdTest() {
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