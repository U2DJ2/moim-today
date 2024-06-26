package moim_today.implement.moim.moim;

import moim_today.domain.moim.MoimSortedFilter;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.dto.moim.moim.MyMoimResponse;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_CAPACITY_ERROR;
import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MoimFinderTest extends ImplementTest {

    @Autowired
    private MoimFinder moimFinder;

    private Random random = new Random();

    private final int MOIM_MEMBER_SIZE = 3;

    @DisplayName("회원이 참여한 모임의 정보를 가져온다.")
    @Test
    void findAllMyMoimResponse() {
        // given 1
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);

        // given 2
        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity1);
        moimRepository.save(moimJpaEntity2);

        // given 3
        JoinedMoimJpaEntity joinedMoimJpaEntity1 = JoinedMoimJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .moimId(moimJpaEntity1.getId())
                .build();

        JoinedMoimJpaEntity joinedMoimJpaEntity2 = JoinedMoimJpaEntity.builder()
                .memberId(memberJpaEntity.getId())
                .moimId(moimJpaEntity2.getId())
                .build();

        joinedMoimRepository.save(joinedMoimJpaEntity1);
        joinedMoimRepository.save(joinedMoimJpaEntity2);

        // when
        List<MyMoimResponse> myMoimResponses = moimFinder.findAllMyMoimResponse(memberJpaEntity.getId());

        // then
        assertThat(myMoimResponses.size()).isEqualTo(2);
    }

    @DisplayName("모임을 조회하면 모임 엔티티를 반환한다.")
    @Test
    void getByIdTest() {
        //given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        //when
        MoimJpaEntity findMoimJpaEntity = moimFinder.getById(moimJpaEntity.getId());

        //then
        assertThat(findMoimJpaEntity).isExactlyInstanceOf(MoimJpaEntity.class);
        assertThat(findMoimJpaEntity.getTitle()).isEqualTo(MOIM_TITLE.value());
    }

    @DisplayName("getById로 모임을 조회할 때, 해당하는 모임이 없으면 예외를 발생시킨다.")
    @Test
    void getByIdThrowExceptionTest() {
        //when & then
        assertThatThrownBy(() -> moimFinder.getById(Long.parseLong(MOIM_ID.value())))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MOIM_NOT_FOUND_ERROR.message());
    }

    @DisplayName("모임에 참여한 멤버들만 MoimMemberResponse로 반환한다")
    @Test
    void findJoinedMoimsTest() {
        // given1
        MemberJpaEntity savedMember1 = saveRandomMember();
        MemberJpaEntity savedMember2 = saveRandomMember();
        MemberJpaEntity savedMember3 = saveRandomMember();
        MemberJpaEntity savedMember4 = saveRandomMember();

        // given2
        MoimJpaEntity savedMoim = saveMoim(savedMember1.getId());

        // given3
        JoinedMoimJpaEntity saveJoinedMoim1 = saveJoinedMoim(savedMoim.getId(), savedMember1.getId());
        JoinedMoimJpaEntity saveJoinedMoim2 = saveJoinedMoim(savedMoim.getId(), savedMember2.getId());
        JoinedMoimJpaEntity saveJoinedMoim3 = saveJoinedMoim(savedMoim.getId(), savedMember3.getId());


        // when
        List<JoinedMoimJpaEntity> joinedMoimMembers = moimFinder.findJoinedMoims(savedMoim.getId());

        List<Long> moimMemberIds = new ArrayList<>();
        joinedMoimMembers.stream().forEach(m -> moimMemberIds.add(m.getMemberId()));

        // then
        assertThat(joinedMoimMembers.size()).isEqualTo(MOIM_MEMBER_SIZE);
        assertThat(joinedMoimMembers.get(random.nextInt(MOIM_MEMBER_SIZE)).getMemberId()).isIn(savedMember1.getId(),
                savedMember2.getId(),
                savedMember3.getId()
        );
        assertThat(savedMember4.getId()).isNotIn(moimMemberIds);
    }

    @DisplayName("모임에 참여한 멤버들만 조회한다")
    @Test
    void findMoimMembersTest() {
        // given1
        MemberJpaEntity savedMember1 = saveRandomMember();
        MemberJpaEntity savedMember2 = saveRandomMember();
        MemberJpaEntity savedMember3 = saveRandomMember();
        MemberJpaEntity savedMember4 = saveRandomMember();

        // given2
        MoimJpaEntity savedMoim = saveMoim(savedMember1.getId());

        // given3
        List<JoinedMoimJpaEntity> joinedMoimJpaEntities = new ArrayList<>();
        joinedMoimJpaEntities.add(saveJoinedMoim(savedMoim.getId(), savedMember1.getId()));
        joinedMoimJpaEntities.add(saveJoinedMoim(savedMoim.getId(), savedMember2.getId()));
        joinedMoimJpaEntities.add(saveJoinedMoim(savedMoim.getId(), savedMember3.getId()));

        List<Long> memberIds = JoinedMoimJpaEntity.extractMemberIds(joinedMoimJpaEntities);
        // when
        List<MoimMemberResponse> moimMembers = moimFinder.findMembersInMoim(memberIds,
                savedMoim.getMemberId(), savedMoim.getId());

        List<Long> moimMemberIds = new ArrayList<>();
        moimMembers.forEach(m -> moimMemberIds.add(m.memberId()));

        // then
        assertThat(moimMembers.size()).isEqualTo(MOIM_MEMBER_SIZE);
        assertThat(moimMembers.get(random.nextInt(MOIM_MEMBER_SIZE)).memberId()).isIn(savedMember1.getId(),
                savedMember2.getId(),
                savedMember3.getId()
        );
        assertThat(savedMember4.getId()).isNotIn(moimMemberIds);
    }

    @DisplayName("멤버가 모임의 호스트인지 검사하는 테스트")
    @Test
    void isHost() {
        // given1
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(1L)
                .build();

        MoimJpaEntity savedMoim = moimRepository.save(moimJpaEntity);

        List<JoinedMoimJpaEntity> joinedMoimJpaEntities = new ArrayList<>();

        // given2
        JoinedMoimJpaEntity joinedMoimJpaEntity1 = JoinedMoimJpaEntity.builder()
                .memberId(savedMoim.getMemberId())
                .moimId(savedMoim.getId())
                .build();
        JoinedMoimJpaEntity joinedMoimJpaEntity2 = JoinedMoimJpaEntity.builder()
                .memberId(2L)
                .moimId(savedMoim.getId())
                .build();
        JoinedMoimJpaEntity joinedMoimJpaEntity3 = JoinedMoimJpaEntity.builder()
                .memberId(3L)
                .moimId(savedMoim.getId())
                .build();

        joinedMoimJpaEntities.add(joinedMoimRepository.save(joinedMoimJpaEntity1));
        joinedMoimJpaEntities.add(joinedMoimRepository.save(joinedMoimJpaEntity2));
        joinedMoimJpaEntities.add(joinedMoimRepository.save(joinedMoimJpaEntity3));

        // expected
        assertThat(moimFinder.isHost(1L, savedMoim.getId())).isTrue();
        assertThat(moimFinder.isHost(2L, savedMoim.getId())).isFalse();
    }

    @DisplayName("모임 id로 모임명을 가져온다.")
    @Test
    void getTitleById() {
        // given
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .title(MOIM_TITLE.value())
                .build();

        moimRepository.save(moimJpaEntity);

        // when
        String title = moimFinder.getTitleById(moimJpaEntity.getId());

        // then
        assertThat(title).isEqualTo(MOIM_TITLE.value());
    }

    @DisplayName("모임 id로 모임 기간을 가져온다.")
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

    @DisplayName("필터가 없으면 모든 카테고리의 모임 리스트를 최신 생성 순으로 가져온다.")
    @Test
    void findAllMoim() throws InterruptedException {
        // given
        long universityId = UNIV_ID.longValue();

        MoimJpaEntity firstCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(FIRST_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.TEAM_PROJECT)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(firstCreatedMoimJpaEntity);

        Thread.sleep(10);

        Thread.sleep(10);

        MoimJpaEntity secondCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(SECOND_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.STUDY)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(secondCreatedMoimJpaEntity);

        MoimCategoryDto moimCategoryDto = MoimCategoryDto.ALL;
        MoimSortedFilter moimSortedFilter = null;

        // when
        List<MoimSimpleResponse> moimSimpleResponses = moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);

        // then
        assertThat(moimSimpleResponses.size()).isEqualTo(2);
        assertThat(moimSimpleResponses.get(0).title()).isEqualTo(SECOND_CREATED_MOIM_TITLE.value());
        assertThat(moimSimpleResponses.get(1).title()).isEqualTo(FIRST_CREATED_MOIM_TITLE.value());
    }

    @DisplayName("모임 리스트를 최신 생성 순으로 가져온다.")
    @Test
    void findAllMoimOrderByCreatedAt() throws InterruptedException {
        // given
        long universityId = UNIV_ID.longValue();

        MoimJpaEntity firstCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(FIRST_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.TEAM_PROJECT)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(firstCreatedMoimJpaEntity);

        Thread.sleep(10);

        Thread.sleep(10);

        MoimJpaEntity secondCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(SECOND_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.STUDY)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(secondCreatedMoimJpaEntity);

        MoimCategoryDto moimCategoryDto = MoimCategoryDto.ALL;
        MoimSortedFilter moimSortedFilter = MoimSortedFilter.CREATED_AT;

        // when
        List<MoimSimpleResponse> moimSimpleResponses = moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);

        // then
        assertThat(moimSimpleResponses.size()).isEqualTo(2);
        assertThat(moimSimpleResponses.get(0).title()).isEqualTo(SECOND_CREATED_MOIM_TITLE.value());
        assertThat(moimSimpleResponses.get(1).title()).isEqualTo(FIRST_CREATED_MOIM_TITLE.value());
    }

    @DisplayName("모임 리스트를 조회수 순으로 가져온다.")
    @Test
    void findAllMoimOrderByViews() {
        // given
        long universityId = UNIV_ID.longValue();

        MoimJpaEntity firstCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(FIRST_CREATED_MOIM_TITLE.value())
                .endDate(LocalDate.now().plusDays(1))
                .views(10)
                .build();

        moimRepository.save(firstCreatedMoimJpaEntity);

        MoimJpaEntity secondCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(SECOND_CREATED_MOIM_TITLE.value())
                .endDate(LocalDate.now().plusDays(1))
                .views(20)
                .build();

        moimRepository.save(secondCreatedMoimJpaEntity);

        MoimCategoryDto moimCategoryDto = MoimCategoryDto.ALL;
        MoimSortedFilter moimSortedFilter = MoimSortedFilter.VIEWS;

        // when
        List<MoimSimpleResponse> moimSimpleResponses = moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);

        // then
        assertThat(moimSimpleResponses.size()).isEqualTo(2);
        assertThat(moimSimpleResponses.get(0).title()).isEqualTo(SECOND_CREATED_MOIM_TITLE.value());
        assertThat(moimSimpleResponses.get(1).title()).isEqualTo(FIRST_CREATED_MOIM_TITLE.value());
    }

    @DisplayName("모임 리스트를 카테고리 별로 가져온다.")
    @Test
    void findAllMoimByCategory() {
        // given
        long universityId = UNIV_ID.longValue();

        MoimJpaEntity firstCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(FIRST_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.TEAM_PROJECT)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(firstCreatedMoimJpaEntity);

        MoimJpaEntity secondCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(SECOND_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.STUDY)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(secondCreatedMoimJpaEntity);

        MoimCategoryDto moimCategoryDto = MoimCategoryDto.TEAM_PROJECT;
        MoimSortedFilter moimSortedFilter = null;

        // when
        List<MoimSimpleResponse> moimSimpleResponses = moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);

        // then
        assertThat(moimSimpleResponses.size()).isEqualTo(1);
        assertThat(moimSimpleResponses.get(0).title()).isEqualTo(FIRST_CREATED_MOIM_TITLE.value());
    }

    @DisplayName("자신이 속한 대학교의 모임리스트만 가져온다.")
    @Test
    void findAllMoimByCategoryByUniversityId() {
        // given
        long universityId = UNIV_ID.longValue();
        long otherUniversityId = UNIV_ID.longValue() + 1;

        MoimJpaEntity firstCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(FIRST_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.TEAM_PROJECT)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(firstCreatedMoimJpaEntity);

        MoimJpaEntity secondCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(otherUniversityId)
                .title(SECOND_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.STUDY)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(secondCreatedMoimJpaEntity);

        MoimCategoryDto moimCategoryDto = MoimCategoryDto.ALL;
        MoimSortedFilter moimSortedFilter = null;

        // when
        List<MoimSimpleResponse> moimSimpleResponses = moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);

        // then
        assertThat(moimSimpleResponses.size()).isEqualTo(1);
        assertThat(moimSimpleResponses.get(0).title()).isEqualTo(FIRST_CREATED_MOIM_TITLE.value());
    }

    @DisplayName("모임 기간이 끝나지 않은 모임 리스트만 반환한다..")
    @Test
    void findAllActiveMoimByCategoryByUniversityId() {
        // given
        long universityId = UNIV_ID.longValue();

        MoimJpaEntity firstCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(FIRST_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.TEAM_PROJECT)
                .endDate(LocalDate.now().plusDays(1))
                .build();

        moimRepository.save(firstCreatedMoimJpaEntity);

        MoimJpaEntity secondCreatedMoimJpaEntity = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(SECOND_CREATED_MOIM_TITLE.value())
                .moimCategory(MoimCategory.STUDY)
                .endDate(LocalDate.now().minusDays(1))
                .build();

        moimRepository.save(secondCreatedMoimJpaEntity);

        MoimCategoryDto moimCategoryDto = MoimCategoryDto.ALL;
        MoimSortedFilter moimSortedFilter = null;

        // when
        List<MoimSimpleResponse> moimSimpleResponses = moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);

        // then
        assertThat(moimSimpleResponses.size()).isEqualTo(1);
        assertThat(moimSimpleResponses.get(0).title()).isEqualTo(FIRST_CREATED_MOIM_TITLE.value());
    }

    @DisplayName("모임에 여석이 있는지 검사하고, 꽉 차있으면 에러를 발생시킨다.")
    @Test
    void validateCapacityThrowError() {
        // given1
        MemberJpaEntity member1 = saveRandomMember();
        MemberJpaEntity member2 = saveRandomMember();
        MemberJpaEntity member3 = saveRandomMember();
        long hostId = member1.getId();

        // given2
        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .memberId(hostId)
                .capacity(1)
                .currentCount(1)
                .build();

        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .memberId(hostId)
                .capacity(3)
                .currentCount(3)
                .build();

        MoimJpaEntity moimJpaEntity3 = MoimJpaEntity.builder()
                .memberId(hostId)
                .capacity(100)
                .currentCount(100)
                .build();

        MoimJpaEntity savedMoim1 = moimRepository.save(moimJpaEntity1);
        MoimJpaEntity savedMoim2 = moimRepository.save(moimJpaEntity2);
        MoimJpaEntity savedMoim3 = moimRepository.save(moimJpaEntity3);

        // given3
        JoinedMoimJpaEntity jm1 = saveJoinedMoim(savedMoim1.getId(), hostId);
        JoinedMoimJpaEntity jm2 = saveJoinedMoim(savedMoim1.getId(), member2.getId());
        JoinedMoimJpaEntity jm3 = saveJoinedMoim(savedMoim1.getId(), member3.getId());
        JoinedMoimJpaEntity jm4 = saveJoinedMoim(savedMoim2.getId(), hostId);
        JoinedMoimJpaEntity jm5 = saveJoinedMoim(savedMoim2.getId(), member2.getId());
        JoinedMoimJpaEntity jm6 = saveJoinedMoim(savedMoim2.getId(), member3.getId());

        // expected
        assertThatThrownBy(() -> moimFinder.validateCapacity(savedMoim1))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MOIM_CAPACITY_ERROR.message());
        assertThatThrownBy(() -> moimFinder.validateCapacity(savedMoim2))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MOIM_CAPACITY_ERROR.message());
        assertThatThrownBy(() -> moimFinder.validateCapacity(savedMoim3))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(MOIM_CAPACITY_ERROR.message());
    }

    @DisplayName("모임에 여석이 있는지 검사하고, 여석이 있으면 에러를 발생시키지 않는다.")
    @Test
    void validateCapacityDoesNotThrowError() {
        // given1
        MemberJpaEntity member1 = saveRandomMember();
        MemberJpaEntity member2 = saveRandomMember();
        MemberJpaEntity member3 = saveRandomMember();
        long hostId = member1.getId();

        // given2
        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .memberId(hostId)
                .capacity(4)
                .currentCount(3)
                .build();

        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .memberId(hostId)
                .capacity(100)
                .currentCount(1)
                .build();

        MoimJpaEntity moimJpaEntity3 = MoimJpaEntity.builder()
                .memberId(hostId)
                .capacity(20)
                .currentCount(19)
                .build();

        MoimJpaEntity savedMoim1 = moimRepository.save(moimJpaEntity1);
        MoimJpaEntity savedMoim2 = moimRepository.save(moimJpaEntity2);

        // given3
        JoinedMoimJpaEntity jm1 = saveJoinedMoim(savedMoim1.getId(), hostId);
        JoinedMoimJpaEntity jm2 = saveJoinedMoim(savedMoim1.getId(), member2.getId());
        JoinedMoimJpaEntity jm3 = saveJoinedMoim(savedMoim1.getId(), member3.getId());
        JoinedMoimJpaEntity jm4 = saveJoinedMoim(savedMoim2.getId(), hostId);
        JoinedMoimJpaEntity jm5 = saveJoinedMoim(savedMoim2.getId(), member2.getId());
        JoinedMoimJpaEntity jm6 = saveJoinedMoim(savedMoim2.getId(), member3.getId());

        // expected
        assertThatCode(() -> moimFinder.validateCapacity(savedMoim1))
                .doesNotThrowAnyException();
        assertThatCode(() -> moimFinder.validateCapacity(savedMoim2))
                .doesNotThrowAnyException();
        assertThatCode(() -> moimFinder.validateCapacity(savedMoim2))
                .doesNotThrowAnyException();
    }

    @DisplayName("검색어로 모임을 검색한다.")
    @Test
    void searchMoimBySearchParam() {
        // given1
        long universityId = UNIV_ID.longValue();

        LocalDate endDate = LocalDate.now().plusDays(1);
        MoimJpaEntity moimA = MoimJpaEntity.builder()
                .universityId(universityId)
                .title("appleMango")
                .endDate(endDate)
                .build();

        // given2
        MoimJpaEntity moimB = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(" " + "apple" + " " + "mango" + " ")
                .endDate(endDate)
                .build();

        // given3
        MoimJpaEntity moimC = MoimJpaEntity.builder()
                .universityId(universityId)
                .title("apple" + " " + "mango")
                .endDate(endDate)
                .build();

        moimRepository.save(moimA);
        moimRepository.save(moimB);
        moimRepository.save(moimC);

        //when
        List<MoimSimpleResponse> appleResponses = moimFinder.searchMoim(universityId, "apple");
        List<MoimSimpleResponse> mangoResponses = moimFinder.searchMoim(universityId, "mango");
        List<MoimSimpleResponse> blankResponses = moimFinder.searchMoim(universityId, " ");
        List<MoimSimpleResponse> noneResponses = moimFinder.searchMoim(universityId, "none");
        List<MoimSimpleResponse> applemangoResponses = moimFinder.searchMoim(universityId, "apple mango");

        //then
        assertThat(appleResponses.size()).isEqualTo(3);
        assertThat(mangoResponses.size()).isEqualTo(3);
        assertThat(blankResponses.size()).isEqualTo(3);
        assertThat(noneResponses.size()).isEqualTo(0);
        assertThat(applemangoResponses.size()).isEqualTo(2);
    }

    @DisplayName("다른 대학 모임은 검색되지 않는다.")
    @Test
    void searchMoimBySearchParamAndUniversityId() {
        // given1
        long universityId = UNIV_ID.longValue();
        long otherUniversityId = UNIV_ID.longValue() + 1;

        LocalDate endDate = LocalDate.now().plusDays(1);
        MoimJpaEntity moimA = MoimJpaEntity.builder()
                .universityId(universityId)
                .title("appleMango")
                .endDate(endDate)
                .build();

        // given2
        MoimJpaEntity moimB = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(" " + "apple" + " " + "mango" + " ")
                .endDate(endDate)
                .build();

        // given3
        MoimJpaEntity moimC = MoimJpaEntity.builder()
                .universityId(otherUniversityId)
                .title("apple" + " " + "mango")
                .endDate(endDate)
                .build();

        moimRepository.save(moimA);
        moimRepository.save(moimB);
        moimRepository.save(moimC);

        //when
        List<MoimSimpleResponse> appleResponses = moimFinder.searchMoim(universityId, "apple");
        List<MoimSimpleResponse> mangoResponses = moimFinder.searchMoim(universityId, "mango");
        List<MoimSimpleResponse> blankResponses = moimFinder.searchMoim(universityId, " ");
        List<MoimSimpleResponse> noneResponses = moimFinder.searchMoim(universityId, "none");
        List<MoimSimpleResponse> applemangoResponses = moimFinder.searchMoim(otherUniversityId, "apple mango");

        //then
        assertThat(appleResponses.size()).isEqualTo(2);
        assertThat(mangoResponses.size()).isEqualTo(2);
        assertThat(blankResponses.size()).isEqualTo(2);
        assertThat(noneResponses.size()).isEqualTo(0);
        assertThat(applemangoResponses.size()).isEqualTo(1);
    }

    @DisplayName("모임 검색시 기간이 종료된 모임은 검색되지 않는다.")
    @Test
    void searchActiveMoimBySearchParamAndUniversityId() {
        // given1
        long universityId = UNIV_ID.longValue();

        LocalDate inProgressDate = LocalDate.now().plusDays(1);
        LocalDate endedDate = LocalDate.now().minusDays(1);
        MoimJpaEntity moimA = MoimJpaEntity.builder()
                .universityId(universityId)
                .title("appleMango")
                .endDate(inProgressDate)
                .build();

        // given2
        MoimJpaEntity moimB = MoimJpaEntity.builder()
                .universityId(universityId)
                .title(" " + "apple" + " " + "mango" + " ")
                .endDate(inProgressDate)
                .build();

        // given3
        MoimJpaEntity moimC = MoimJpaEntity.builder()
                .universityId(universityId)
                .title("apple" + " " + "mango")
                .endDate(endedDate)
                .build();

        moimRepository.save(moimA);
        moimRepository.save(moimB);
        moimRepository.save(moimC);

        //when
        List<MoimSimpleResponse> appleResponses = moimFinder.searchMoim(universityId, "apple");

        //then
        assertThat(appleResponses.size()).isEqualTo(2);
    }

    @DisplayName("모임들 중 완료된 모임을 반환한다.")
    @Test
    void findEndedMoimSimpleResponsesByMoimIds() {
        MemberJpaEntity saveMember = saveRandomMember();

        LocalDate localDate1 = LocalDate.of(2023, 5, 12);
        LocalDate localDate2 = LocalDate.of(2024, 5, 16);
        LocalDate localDate3 = LocalDate.of(2025, 6, 5);

        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .memberId(saveMember.getId())
                .endDate(localDate1)
                .build();
        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .memberId(saveMember.getId())
                .endDate(localDate2)
                .build();
        MoimJpaEntity moimJpaEntity3 = MoimJpaEntity.builder()
                .memberId(saveMember.getId())
                .endDate(localDate3)
                .build();

        moimRepository.save(moimJpaEntity1);
        moimRepository.save(moimJpaEntity2);
        moimRepository.save(moimJpaEntity3);

        List<Long> moimIds = List.of(moimJpaEntity1.getId(), moimJpaEntity2.getId(), moimJpaEntity3.getId());

        List<MoimSimpleResponse> endedMoims1 = moimFinder.findEndedMoimSimpleResponsesByMoimIds(
                moimIds, LocalDate.of(2024, 5, 16));
        List<MoimSimpleResponse> endedMoims2 = moimFinder.findEndedMoimSimpleResponsesByMoimIds(
                moimIds, LocalDate.of(2025, 6, 4));

        assertThat(endedMoims1.size()).isEqualTo(1);
        assertThat(endedMoims2.size()).isEqualTo(2);
    }

    @DisplayName("모임들 중 진행중인 모임을 반환한다.")
    @Test
    void findInProgressMoimSimpleResponsesByMoimIds() {
        MemberJpaEntity saveMember = saveRandomMember();

        LocalDate localDate1 = LocalDate.of(2023, 5, 12);
        LocalDate localDate2 = LocalDate.of(2024, 5, 16);
        LocalDate localDate3 = LocalDate.of(2025, 6, 5);

        MoimJpaEntity moimJpaEntity1 = MoimJpaEntity.builder()
                .memberId(saveMember.getId())
                .endDate(localDate1)
                .build();
        MoimJpaEntity moimJpaEntity2 = MoimJpaEntity.builder()
                .memberId(saveMember.getId())
                .endDate(localDate2)
                .build();
        MoimJpaEntity moimJpaEntity3 = MoimJpaEntity.builder()
                .memberId(saveMember.getId())
                .endDate(localDate3)
                .build();

        moimRepository.save(moimJpaEntity1);
        moimRepository.save(moimJpaEntity2);
        moimRepository.save(moimJpaEntity3);

        List<Long> moimIds = List.of(moimJpaEntity1.getId(), moimJpaEntity2.getId(), moimJpaEntity3.getId());

        List<MoimSimpleResponse> inProgressMoims1 = moimFinder.findInProgressMoimSimpleResponsesByMoimIds(
                moimIds, LocalDate.of(2024, 5, 16));
        List<MoimSimpleResponse> inProgressMoims2 = moimFinder.findInProgressMoimSimpleResponsesByMoimIds(
                moimIds, LocalDate.of(2025, 6, 4));

        assertThat(inProgressMoims1.size()).isEqualTo(2);
        assertThat(inProgressMoims2.size()).isEqualTo(1);
    }

    private MemberJpaEntity saveRandomMember() {
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .username(USERNAME + String.valueOf(random.nextInt(10)))
                .build();

        return memberRepository.save(memberJpaEntity);
    }

    private MoimJpaEntity saveMoim(final long hostMemberId) {
        MoimJpaEntity moimJpaEntity = MoimJpaEntity.builder()
                .memberId(hostMemberId)
                .build();

        return moimRepository.save(moimJpaEntity);
    }

    private JoinedMoimJpaEntity saveJoinedMoim(final long moimId, final long joinMemberId) {
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                .memberId(joinMemberId)
                .moimId(moimId)
                .build();

        return joinedMoimRepository.save(joinedMoimJpaEntity);
    }
}
