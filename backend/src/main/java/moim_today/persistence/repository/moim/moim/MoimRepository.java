package moim_today.persistence.repository.moim.moim;

import moim_today.domain.moim.MoimSortedFilter;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.dto.moim.moim.MyMoimResponse;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

import java.util.List;

public interface MoimRepository {

    MoimJpaEntity save(final MoimJpaEntity moimJpaEntity);

    long count();

    MoimJpaEntity getById(final long moimId);

    String getTitleById(final long moimId);

    MoimDateResponse findMoimDate(final long moimId);

    void deleteById(final long moimId);

    List<MoimSimpleResponse> findAllMoimResponse(final MoimCategoryDto moimCategoryDto, final MoimSortedFilter moimSortedFilter);

    List<MyMoimResponse> findAllMyMoimResponse(final List<Long> moimIds);

    MoimJpaEntity getByIdWithPessimisticLock(final long moimId);

    List<MoimSimpleResponse> searchMoimBySearchParam(final String searchParam);
}
