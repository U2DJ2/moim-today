package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.MOIM_ID;
import static moim_today.util.TestConstant.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoimFinderTest extends ImplementTest {

    @Autowired
    private MoimFinder moimFinder;

    @DisplayName("getById로 모임을 조회하면 모임 엔티티를 반환한다.")
    @Test
    void getByIdTest(){
        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        //when
        MoimJpaEntity findMoimJpaEntity = moimFinder.getById(moimJpaEntity.getId());

        //then
        assertThat(findMoimJpaEntity).isExactlyInstanceOf(MoimJpaEntity.class);
        assertThat(findMoimJpaEntity.getTitle()).isEqualTo(TITLE.value());
    }

    @DisplayName("getById로 모임을 조회할 때, 해당하는 모임이 없으면 예외를 발생시킨다.")
    @Test
    void getByIdThrowExceptionTest(){
        //when & then
        assertThatThrownBy(() -> moimFinder.getById(Long.parseLong(MOIM_ID.value())))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MOIM_NOT_FOUND_ERROR.message());
    }

    @DisplayName("모임 id로 모임명을 가져온다.")
    @Test
    void getTitleById() {
        // given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // when
        String title = moimFinder.getTitleById(moimJpaEntity.getId());

        // then
        assertThat(title).isEqualTo(TITLE.value());
    }

    @DisplayName("모임 id로 모임명을 가져온다.")
    @Test
    void findMoimDate() {
        // given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .startDate(LocalDate.of(2024, 3, 4))
                .endDate(LocalDate.of(2024, 6, 30))
                .build();

        moimRepository.save(moimJpaEntity);

        // when
        MoimDateResponse moimDateResponse = moimFinder.findMoimDate(moimJpaEntity.getId());

        // then
        assertThat(moimDateResponse.startDate()).isEqualTo(LocalDate.of(2024, 3, 4));
        assertThat(moimDateResponse.endDate()).isEqualTo(LocalDate.of(2024, 6, 30));
    }
}
