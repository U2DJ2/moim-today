package moim_today.persistence.repository.moim.moim;

import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.dto.moim.moim.filter.MoimFilterRequest;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

import java.util.List;

public interface MoimRepository {

    MoimJpaEntity save(final MoimJpaEntity moimJpaEntity);

    long count();

    MoimJpaEntity getById(final long moimId);

    String getTitleById(final long moimId);

    MoimDateResponse findMoimDate(final long moimId);

    void deleteById(final long moimId);

    long getMemberIdById(final long moimId);

    List<MoimSimpleResponse> findAllMoimResponse(final MoimFilterRequest moimFilterRequest);

    MoimJpaEntity getByIdWithPessimisticLock(final long moimId);
}
