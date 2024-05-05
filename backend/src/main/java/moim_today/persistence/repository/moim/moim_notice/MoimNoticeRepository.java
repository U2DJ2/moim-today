package moim_today.persistence.repository.moim.moim_notice;

import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;

import java.util.List;

public interface MoimNoticeRepository {

    void save(final MoimNoticeJpaEntity moimNoticeJpaEntity);

    long count();

    List<MoimNoticeSimpleResponse> findAllMoimNotice(final long moimId);

    MoimNoticeJpaEntity getById(final long moimNoticeId);

    void deleteById(final long moimNoticeId);
}
