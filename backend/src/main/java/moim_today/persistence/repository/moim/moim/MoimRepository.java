package moim_today.persistence.repository.moim.moim;

import moim_today.domain.moim.MoimSortedFilter;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.dto.moim.moim.MyMoimResponse;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

import java.time.LocalDate;
import java.util.List;

public interface MoimRepository {

    MoimJpaEntity save(final MoimJpaEntity moimJpaEntity);

    long count();

    MoimJpaEntity getById(final long moimId);

    List<MoimSimpleResponse> findAllByUniversityId(final long universityId);

    String getTitleById(final long moimId);

    MoimDateResponse findMoimDate(final long moimId);

    void deleteById(final long moimId);

    List<MoimSimpleResponse> findAllMoimResponses(final long universityId, final MoimCategoryDto moimCategoryDto, final MoimSortedFilter moimSortedFilter);

    List<MyMoimResponse> findAllMyMoimResponse(final List<Long> moimIds);

    MoimJpaEntity getByIdWithPessimisticLock(final long moimId);

    List<MoimSimpleResponse> searchMoimBySearchParam(final long universityId, final String searchParam);

    List<MoimSimpleResponse> findEndedMoimSimpleResponsesByMoimIds(final List<Long> moimIds, final LocalDate now);

    List<MoimSimpleResponse> findInProgressMoimSimpleResponsesByMoimIds(final List<Long> moimIds, final LocalDate now);
}
