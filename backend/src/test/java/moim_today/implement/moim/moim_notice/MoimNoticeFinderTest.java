package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.NOTICE_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoimNoticeFinderTest extends ImplementTest {

    @Autowired
    private MoimNoticeFinder moimNoticeFinder;

    @DisplayName("모임Id로 모든 공지를 찾는다.")
    @Test
    void findAllMoimNoticeTest() {
        //given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();

        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        //given
        MoimNoticeJpaEntity moimNoticeJpaEntity1 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        //given
        MoimNoticeJpaEntity moimNoticeJpaEntity2 = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .build();

        moimNoticeRepository.save(moimNoticeJpaEntity1);
        moimNoticeRepository.save(moimNoticeJpaEntity2);

        //when
        List<MoimNoticeSimpleResponse> moimNoticeResponses = moimNoticeFinder
                .findAllMoimNotice(memberId, moimId);

        //then
        assertThat(moimNoticeResponses.size()).isEqualTo(2);
    }

    @DisplayName("공지Id로 공지 정보를 조회한다.")
    @Test
    void getMoimNoticeTest() {
        //given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();

        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        //given
        MoimNoticeJpaEntity moimNoticeJpaEntity = MoimNoticeJpaEntity
                .builder()
                .moimId(moimId)
                .title(NOTICE_TITLE.value())
                .build();
        
        moimNoticeRepository.save(moimNoticeJpaEntity);
        long noticeId = moimNoticeJpaEntity.getId();

        //when
        MoimNoticeJpaEntity findEntity = moimNoticeFinder.getById(memberId, noticeId);

        //then
        assertThat(findEntity.getTitle()).isEqualTo(NOTICE_TITLE.value());
    }

    @DisplayName("존재하지 않는 모임을 조회하려고 하면 예외가 발생한다")
    @Test
    void getMoimNoticeNotFoundTest() {
        //given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();

        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity);

        //expected
        assertThatThrownBy(() -> moimNoticeFinder.getById(memberId, NOTICE_ID.longValue()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NOTICE_NOT_FOUND_ERROR.message());
    }
}