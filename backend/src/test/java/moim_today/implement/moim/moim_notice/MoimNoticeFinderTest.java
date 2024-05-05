package moim_today.implement.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN;
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

    @DisplayName("모임에 속한 회원이 아니라면 공지 조회에 실패한다.")
    @Test
    void findAllMoimNoticeForbiddenTest() {
        //given
        long moimId = MOIM_ID.longValue();
        long memberId = MEMBER_ID.longValue();
        long forbiddenMoimId = FORBIDDEN_MOIM_ID.longValue();

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

        //expected
        assertThatThrownBy(() -> moimNoticeFinder
                .findAllMoimNotice(memberId, forbiddenMoimId))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(MOIM_FORBIDDEN.message());
    }

}