package moim_today.implement.university;

import moim_today.dto.university.UniversityInfoResponse;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
                    .universityName("testUniv"+i)
                    .universityEmail("testEmail"+i)
                    .build();
            universityRepository.save(universityJpaEntity);
            actualUnivs.add(UniversityInfoResponse.of(universityJpaEntity));
        }

        // when
        List<UniversityInfoResponse> getAllUniv = universityFinder.getAllUniversity();

        // then
        assertThat(getAllUniv).isEqualTo(actualUnivs);
    }
}