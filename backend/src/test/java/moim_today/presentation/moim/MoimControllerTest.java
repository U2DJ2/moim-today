package moim_today.presentation.moim;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.moim.moim.MoimService;
import moim_today.application.moim.moim_notice.MoimNoticeService;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.MoimSortedFilter;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.*;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.dto.moim.moim_notice.MoimNoticeDeleteRequest;
import moim_today.dto.moim.moim_notice.MoimNoticeUpdateRequest;
import moim_today.fake_class.moim.FakeMoimNoticeService;
import moim_today.fake_class.moim.FakeMoimService;
import moim_today.util.ControllerTest;
import moim_today.util.EnumDocsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MoimControllerTest extends ControllerTest {

    private final MoimService fakeMoimService = new FakeMoimService();
    private final MoimNoticeService fakeMoimNoticeService = new FakeMoimNoticeService();

    @Override
    protected Object initController() {
        return new MoimController(fakeMoimService, fakeMoimNoticeService);
    }

    @DisplayName("로그인한 회원이 참여한 모임리스트를 조회한다.")
    @Test
    void findAllMyMoimResponse() throws Exception {
        mockMvc.perform(
                        get("/api/moims")
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원이 참여한 모임 리스트 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("로그인한 회원이 참여한 모임 리스트 간단한 정보 조회")
                                .responseFields(
                                        fieldWithPath("data[0].moimId").type(NUMBER).description("모임 Id"),
                                        fieldWithPath("data[0].title").type(STRING).description("모임명")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임을 생성한다.")
    @Test
    void createPrivateMoimApiTest() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
                MOIM_TITLE.value(),
                MOIM_CONTENTS.value(),
                Integer.parseInt(CAPACITY.value()),
                PASSWORD.value(),
                MOIM_IMAGE_URL.value(),
                MoimCategory.STUDY,
                DisplayStatus.PRIVATE,
                startDate,
                endDate
        );

        mockMvc.perform(post("/api/moims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimCreateRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("모임 생성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 생성")
                                .requestFields(
                                        fieldWithPath("title").type(STRING).description("모임명"),
                                        fieldWithPath("contents").type(STRING).description("내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("password").type(STRING).description("모임 비밀번호(공개 여부가 PUBLIC일 경우 Nullable)"),
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL(Nullable)"),
                                        fieldWithPath("moimCategory").type(VARIES).description(String.format("카테고리 - %s",
                                                EnumDocsUtils.getEnumNames(MoimCategory.class))),
                                        fieldWithPath("displayStatus").type(VARIES).description(String.format("공개 여부 - %s",
                                                EnumDocsUtils.getEnumNames(DisplayStatus.class))),
                                        fieldWithPath("startDate").type(STRING).description("시작 일자"),
                                        fieldWithPath("endDate").type(STRING).description("종료 일자")
                                )
                                .responseFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 Id")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 사진을 업로드/수정하면 업로드/수정된 파일의 URL을 반환한다. ")
    @Test
    void uploadMoimImageTest() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                FILE_NAME.value(),
                ORIGINAL_FILE_NAME.value(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                FILE_CONTENT.value().getBytes()
        );

        mockMvc.perform(multipart("/api/moims/image")
                        .file(file)
                )
                .andExpect(status().isOk())
                .andDo(document("모임 사진 업로드/수정 성공",
                        requestParts(
                                partWithName("file").description("모임 사진 파일")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 사진 업로드/수정")
                                .responseFields(
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 정보를 조회한다.")
    @Test
    void getMoimDetailTest() throws Exception {

        mockMvc.perform(get("/api/moims/detail/{moimId}", 1))
                .andExpect(status().isOk())
                .andDo(document("모임 정보 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 정보 조회")
                                .pathParameters(
                                        parameterWithName("moimId").description("모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 Id"),
                                        fieldWithPath("title").type(STRING).description("모임명"),
                                        fieldWithPath("contents").type(STRING).description("내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("currentCount").type(NUMBER).description("현재 인원"),
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL"),
                                        fieldWithPath("moimCategory").type(VARIES).description(String.format("카테고리 - %s",
                                                EnumDocsUtils.getEnumNames(MoimCategory.class))),
                                        fieldWithPath("displayStatus").type(VARIES).description(String.format("공개 여부 - %s",
                                                EnumDocsUtils.getEnumNames(DisplayStatus.class))),
                                        fieldWithPath("views").type(NUMBER).description("조회수"),
                                        fieldWithPath("startDate").type(STRING).description("시작 일자"),
                                        fieldWithPath("endDate").type(STRING).description("종료 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임리스트를 조회한다.")
    @Test
    void findAllMoimSimpleResponseTest() throws Exception {

        mockMvc.perform(get("/api/moims/simple")
                        .queryParam("moimCategoryDto", MoimCategoryDto.EXERCISE.name())
                        .queryParam("moimSortedFilter", MoimSortedFilter.CREATED_AT.name()))
                .andExpect(status().isOk())
                .andDo(document("모임 리스트 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 리스트 조회")
                                .queryParameters(
                                        parameterWithName("moimCategoryDto").description(String.format("카테고리 - %s",
                                                EnumDocsUtils.getEnumNames(MoimCategoryDto.class))),
                                        parameterWithName("moimSortedFilter").description(String.format("정렬 기준 - %s",
                                                EnumDocsUtils.getEnumNames(MoimSortedFilter.class)))
                                )
                                .responseFields(
                                        fieldWithPath("data[0].moimId").type(NUMBER).description("모임 Id"),
                                        fieldWithPath("data[0].title").type(STRING).description("모임명"),
                                        fieldWithPath("data[0].capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("data[0].currentCount").type(NUMBER).description("현재 인원"),
                                        fieldWithPath("data[0].imageUrl").type(STRING).description("모임 사진 URL"),
                                        fieldWithPath("data[0].moimCategory").type(VARIES).description(String.format("카테고리 - %s",
                                                EnumDocsUtils.getEnumNames(MoimCategory.class))),
                                        fieldWithPath("data[0].displayStatus").type(VARIES).description(String.format("공개 여부 - %s",
                                                EnumDocsUtils.getEnumNames(DisplayStatus.class)))
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 정보를 수정한다.")
    @Test
    void updateMoimTest() throws Exception {
        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .moimId(Long.parseLong(MOIM_ID.value()))
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(Integer.parseInt(CAPACITY.value()))
                .imageUrl(MOIM_IMAGE_URL.value())
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(LocalDate.of(2024, 3, 1))
                .endDate(LocalDate.of(2024, 6, 30))
                .build();

        mockMvc.perform(patch("/api/moims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(document("모임 정보 수정 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 정보 수정")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("수정할 모임 ID"),
                                        fieldWithPath("title").type(STRING).description("수정한 모임명"),
                                        fieldWithPath("contents").type(STRING).description("수정한 내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("수정한 모집 인원"),
                                        fieldWithPath("imageUrl").type(STRING).description("수정한 모임 사진 URL"),
                                        fieldWithPath("password").type(STRING).description("수정한 비밀번호"),
                                        fieldWithPath("moimCategory").type(VARIES).description(String.format("수정한 카테고리 - %s",
                                                EnumDocsUtils.getEnumNames(MoimCategory.class))),
                                        fieldWithPath("displayStatus").type(VARIES).description(String.format("수정한 공개 여부 - %s",
                                                EnumDocsUtils.getEnumNames(DisplayStatus.class))),
                                        fieldWithPath("startDate").type(STRING).description("수정한 시작일자"),
                                        fieldWithPath("endDate").type(STRING).description("수정한 종료일자")
                                ).build()
                        )));
    }

    @DisplayName("모임을 삭제한다.")
    @Test
    void deleteMoimTest() throws Exception {
        MoimDeleteRequest moimDeleteRequest = new MoimDeleteRequest(Long.parseLong(MOIM_ID.value()));

        mockMvc.perform(delete("/api/moims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(document("모임 삭제 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 삭제")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("삭제할 모임 ID")
                                ).build()
                        )));
    }

    @DisplayName("모임에서 멤버를 조회한다")
    @Test
    void showMoimMemberTest() throws Exception {
        mockMvc.perform(get("/api/moims/members/{moimId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("모임 멤버 조회",
                        pathParameters(
                                parameterWithName("moimId").description("모임 ID")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 조회 성공")
                                .responseFields(
                                        fieldWithPath("isHostRequest").type(BOOLEAN).description("호스트의 요청 여부"),
                                        fieldWithPath("moimMembers[].isHost").type(BOOLEAN).description("해당 멤버가 호스트인지 여부"),
                                        fieldWithPath("moimMembers[].memberId").type(NUMBER).description("멤버 ID"),
                                        fieldWithPath("moimMembers[].memberName").type(STRING).description("멤버 이름"),
                                        fieldWithPath("moimMembers[].joinedDate").type(STRING).description("참여 날짜")
                                                .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss"),
                                                        key("timezone").value("Asia/Seoul")),
                                        fieldWithPath("moimMembers[].profileImageUrl").type(STRING).description("프로필 이미지 URL")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임에서 멤버를 강퇴시킨다")
    @Test
    void deleteForceMoimMemberTest() throws Exception {
        MoimMemberKickRequest moimMemberKickRequest = MoimMemberKickRequest.builder()
                .deleteMemberId(MEMBER_ID.longValue() + 1L)
                .moimId(MOIM_ID.longValue())
                .build();

        mockMvc.perform(delete("/api/moims/members/kick")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimMemberKickRequest)))
                .andExpect(status().isOk())
                .andDo(document("모임 멤버 강제 퇴장 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 강제 퇴장")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("강퇴가 일어날 모임 ID"),
                                        fieldWithPath("deleteMemberId").type(NUMBER).description("강퇴시킬 멤버 ID")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임에서 멤버를 강퇴시킬 때 호스트가 아닌 경우 에러가 발생한다")
    @Test
    void deleteForceMoimMemberNotHostTest() throws Exception {
        MoimMemberKickRequest moimMemberKickRequest = MoimMemberKickRequest.builder()
                .deleteMemberId(MEMBER_ID.longValue() + 1L)
                .moimId(MOIM_ID.longValue() + 1L)
                .build();

        mockMvc.perform(delete("/api/moims/members/kick")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimMemberKickRequest)))
                .andExpect(status().isForbidden())
                .andDo(document("모임에서 호스트가 아닐 경우 멤버 강제 퇴장 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 강제 퇴장")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("강퇴가 일어날 모임 ID"),
                                        fieldWithPath("deleteMemberId").type(NUMBER).description("강퇴시킬 멤버 ID")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임에서 강퇴할 멤버가 호스트인 경우 에러가 발생한다")
    @Test
    void deleteForceMoimMemberHostFailTest() throws Exception {
        MoimMemberKickRequest moimMemberKickRequest = MoimMemberKickRequest.builder()
                .deleteMemberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .build();

        mockMvc.perform(delete("/api/moims/members/kick")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimMemberKickRequest)))
                .andExpect(status().isForbidden())
                .andDo(document("모임에서 강제 퇴장할 멤버가 호스트일 경우 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 강제 퇴장")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("강퇴가 일어날 모임 ID"),
                                        fieldWithPath("deleteMemberId").type(NUMBER).description("강퇴시킬 멤버 ID")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임에서 멤버가 탈퇴한다")
    @Test
    void deleteMoimMemberTest() throws Exception {
        MoimMemberDeleteRequest moimMemberDeleteRequest = MoimMemberDeleteRequest.builder()
                .moimId(MOIM_ID.longValue() + 2L)
                .build();

        mockMvc.perform(delete("/api/moims/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimMemberDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(document("모임 멤버 삭제 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 삭제")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("탈퇴할 모임 ID")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임에서 탈퇴할 멤버가 호스트면 실패한다")
    @Test
    void deleteMoimMemberFailTest() throws Exception {
        MoimMemberDeleteRequest moimMemberDeleteRequest = MoimMemberDeleteRequest.builder()
                .moimId(MOIM_ID.longValue())
                .build();

        mockMvc.perform(delete("/api/moims/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimMemberDeleteRequest)))
                .andExpect(status().isForbidden())
                .andDo(document("모임에서 삭제할 멤버가 호스트면 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 삭제")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("탈퇴할 모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 참여에 성공한다")
    @Test
    void appendMoimMemberTest() throws Exception {
        MoimJoinRequest moimJoinRequest = MoimJoinRequest.builder()
                .moimId(MOIM_ID.longValue())
                .build();

        mockMvc.perform(post("/api/moims/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimJoinRequest)))
                .andExpect(status().isOk())
                .andDo(document("멤버가 모임에 참여 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("멤버가 모임에 참여")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("참여할 모임 ID")
                                )
                                .build()
                        )));
    }

    @DisplayName("이미 모임에 참여한 멤버면 실패한다")
    @Test
    void appendMoimMemberAlreadyJoinedMoimFailTest() throws Exception {
        MoimJoinRequest moimJoinRequest = MoimJoinRequest.builder()
                .moimId(MOIM_ID.longValue() + 1L)
                .build();

        mockMvc.perform(post("/api/moims/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimJoinRequest)))
                .andExpect(status().isBadRequest())
                .andDo(document("이미 모임에 참여한 멤버여서 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("멤버가 모임에 참여")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("참여할 모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임이 없어서 참여에 실패한다")
    @Test
    void appendMoimMemberNotFoundMoimFailTest() throws Exception {
        MoimJoinRequest moimJoinRequest = MoimJoinRequest.builder()
                .moimId(MOIM_ID.longValue() + 2L)
                .build();

        mockMvc.perform(post("/api/moims/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimJoinRequest)))
                .andExpect(status().isNotFound())
                .andDo(document("없는 모임이어서 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("멤버가 모임에 참여")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("참여할 모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임의 여석이 없어서 실패")
    @Test
    void appendMoimMemberCapacityFullMoimFailTest() throws Exception {
        MoimJoinRequest moimJoinRequest = MoimJoinRequest.builder()
                .moimId(MOIM_ID.longValue() + 3L)
                .build();

        mockMvc.perform(post("/api/moims/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimJoinRequest)))
                .andExpect(status().isBadRequest())
                .andDo(document("모임의 여석이 없어서 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("멤버가 모임에 참여")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("참여할 모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 공지를 생성한다.")
    @Test
    void createMoimNoticeTest() throws Exception {
        MoimNoticeCreateRequest moimNoticeCreateRequest = new MoimNoticeCreateRequest(
                MOIM_ID.longValue(),
                NOTICE_TITLE.value(),
                NOTICE_CONTENTS.value());

        mockMvc.perform(post("/api/moims/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimNoticeCreateRequest)))
                .andExpect(status().isOk())
                .andDo(document("모임 공지 생성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("모임 공지 생성")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 Id"),
                                        fieldWithPath("title").type(STRING).description("공지 제목"),
                                        fieldWithPath("contents").type(STRING).description("공지 내용")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 공지를 생성한다. - 주최자가 아니면 예외가 발생한다.")
    @Test
    void createMoimNoticeForbiddenTest() throws Exception {
        MoimNoticeCreateRequest forbiddenRequest = new MoimNoticeCreateRequest(
                FORBIDDEN_MOIM_ID.longValue(),
                NOTICE_TITLE.value(),
                NOTICE_CONTENTS.value());

        mockMvc.perform(post("/api/moims/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forbiddenRequest)))
                .andExpect(status().isForbidden())
                .andDo(document("모임 공지 생성 실패 - 주최자가 아닌 회원이 요청시",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("모임 공지 생성")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 Id"),
                                        fieldWithPath("title").type(STRING).description("공지 제목"),
                                        fieldWithPath("contents").type(STRING).description("공지 내용")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임의 모든 공지를 가져온다.")
    @Test
    void findAllMoimNoticeTest() throws Exception {

        mockMvc.perform(get("/api/moims/notices/simple")
                        .queryParam("moimId", MOIM_ID.value()))
                .andExpect(status().isOk())
                .andDo(document("모든 공지 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("모든 공지 조회")
                                .queryParameters(
                                        parameterWithName("moimId").description("모임 Id")
                                )
                                .responseFields(
                                        fieldWithPath("data[0].moimNoticeId").type(NUMBER).description("공지 Id"),
                                        fieldWithPath("data[0].title").type(STRING).description("공지 제목"),
                                        fieldWithPath("data[0].createdAt").type(STRING).description("생성 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임의 모든 공지를 가져온다. - 모임의 소속이 아니면 조회할 수 없다.")
    @Test
    void findAllMoimNoticeForbiddenTest() throws Exception {

        mockMvc.perform(get("/api/moims/notices/simple")
                        .queryParam("moimId", FORBIDDEN_MOIM_ID.value()))
                .andExpect(status().isForbidden())
                .andDo(document("모든 공지 조회 실패 - 모임에 소속되지 않은 회원이 요청했을 때",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("모든 공지 조회")
                                .queryParameters(
                                        parameterWithName("moimId").description("접근 권한이 없는 모임 Id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("공지를 클릭해 확인한다.")
    @Test
    void getMoimNoticeDetailTest() throws Exception {

        mockMvc.perform(get("/api/moims/notices/detail")
                        .queryParam("moimNoticeId", NOTICE_ID.value()))
                .andExpect(status().isOk())
                .andDo(document("공지 정보 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("공지 정보 조회")
                                .queryParameters(
                                        parameterWithName("moimNoticeId").description("공지 Id")
                                )
                                .responseFields(
                                        fieldWithPath("moimNoticeId").type(NUMBER).description("공지 Id"),
                                        fieldWithPath("title").type(STRING).description("공지 제목"),
                                        fieldWithPath("contents").type(STRING).description("공지 내용"),
                                        fieldWithPath("createdAt").type(STRING).description("생성 일자"),
                                        fieldWithPath("lastModifiedAt").type(STRING).description("수정 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("공지를 클릭해 확인한다. - 모임의 소속이 아니면 조회할 수 없다.")
    @Test
    void getMoimNoticeDetailForbiddenTest() throws Exception {

        mockMvc.perform(get("/api/moims/notices/detail")
                        .queryParam("moimNoticeId", FORBIDDEN_NOTICE_ID.value()))
                .andExpect(status().isForbidden())
                .andDo(document("공지 정보 조회 실패 - 모임에 소속되지 않은 회원이 요청했을 때",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("공지 정보 조회")
                                .queryParameters(
                                        parameterWithName("moimNoticeId").description("접근 권한이 없는 공지 Id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("공지를 수정한다.")
    @Test
    void updateMoimNoticeTest() throws Exception {
        MoimNoticeUpdateRequest moimNoticeUpdateRequest = MoimNoticeUpdateRequest.builder()
                .moimNoticeId(NOTICE_ID.longValue())
                .title(NOTICE_TITLE.value())
                .contents(NOTICE_CONTENTS.value())
                .build();

        mockMvc.perform(patch("/api/moims/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimNoticeUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(document("공지 정보 수정 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("공지 정보 수정")
                                .requestFields(
                                        fieldWithPath("moimNoticeId").type(NUMBER).description("수정할 공지 Id"),
                                        fieldWithPath("title").type(STRING).description("수정할 공지 제목"),
                                        fieldWithPath("contents").type(STRING).description("수정할 공지 내용")
                                )
                                .build()
                        )));
    }

    @DisplayName("공지를 수정한다. - 주최자가 아니면 예외가 발생한다.")
    @Test
    void updateMoimNoticeForbiddenTest() throws Exception {
        MoimNoticeUpdateRequest forbiddenRequest = MoimNoticeUpdateRequest.builder()
                .moimNoticeId(FORBIDDEN_NOTICE_ID.longValue())
                .title(NOTICE_TITLE.value())
                .contents(NOTICE_CONTENTS.value())
                .build();

        mockMvc.perform(patch("/api/moims/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forbiddenRequest)))
                .andExpect(status().isForbidden())
                .andDo(document("공지 정보 수정 실패 - 주최자가 아닌 회원이 요청시",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("공지 정보 수정")
                                .requestFields(
                                        fieldWithPath("moimNoticeId").type(NUMBER).description("수정할 공지 Id"),
                                        fieldWithPath("title").type(STRING).description("수정할 공지 제목"),
                                        fieldWithPath("contents").type(STRING).description("수정할 공지 내용")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("공지를 삭제한다.")
    @Test
    void deleteMoimNoticeTest() throws Exception {
        MoimNoticeDeleteRequest deleteRequest = new MoimNoticeDeleteRequest(NOTICE_ID.longValue());

        mockMvc.perform(delete("/api/moims/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andExpect(status().isOk())
                .andDo(document("공지 삭제 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("공지 삭제")
                                .requestFields(
                                        fieldWithPath("moimNoticeId").type(NUMBER).description("삭제할 공지 Id")
                                )
                                .build()
                        )));
    }

    @DisplayName("공지를 삭제한다. - 주최자가 아니면 예외가 발생한다.")
    @Test
    void deleteMoimNoticeForbiddenTest() throws Exception {
        MoimNoticeDeleteRequest forbiddenRequest = new MoimNoticeDeleteRequest(FORBIDDEN_NOTICE_ID.longValue());

        mockMvc.perform(delete("/api/moims/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forbiddenRequest)))
                .andExpect(status().isForbidden())
                .andDo(document("공지 삭제 실패 - 주최자가 아닌 회원이 요청시",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임 공지")
                                .summary("공지 삭제")
                                .requestFields(
                                        fieldWithPath("moimNoticeId").type(NUMBER).description("삭제할 공지 Id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임을 검색한다.")
    @Test
    void searchMoimTest() throws Exception {

        mockMvc.perform(get("/api/moims/search")
                        .queryParam("searchParam", "검색어"))
                .andExpect(status().isOk())
                .andDo(document("모임 검색 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 검색")
                                .queryParameters(
                                        parameterWithName("searchParam").description("검색어")
                                )
                                .responseFields(
                                        fieldWithPath("data[0].moimId").type(NUMBER).description("모임 Id"),
                                        fieldWithPath("data[0].title").type(STRING).description("모임명"),
                                        fieldWithPath("data[0].capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("data[0].currentCount").type(NUMBER).description("현재 인원"),
                                        fieldWithPath("data[0].imageUrl").type(STRING).description("모임 사진 URL"),
                                        fieldWithPath("data[0].moimCategory").type(VARIES).description(String.format("카테고리 - %s",
                                                EnumDocsUtils.getEnumNames(MoimCategory.class))),
                                        fieldWithPath("data[0].displayStatus").type(VARIES).description(String.format("공개 여부 - %s",
                                                EnumDocsUtils.getEnumNames(DisplayStatus.class)))
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 카테고리 전체 리스트를 반환한다.")
    @Test
    void returnCategoriesTest() throws Exception {

        mockMvc.perform(get("/api/moims/categories"))
                .andExpect(status().isOk())
                .andDo(document("전체 카테고리 리스트 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("전체 카테고리 리스트 조회")
                                .responseFields(
                                        fieldWithPath("data[]").type(ARRAY).description(String.format("카테고리 - %s",
                                                EnumDocsUtils.getEnumNames(MoimCategory.class)))
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인한 회원이 참여한 모임들을 완료 여부에 따라 카드 정보로 반환한다")
    @Test
    void findAllMyJoinedMoimSimpleResponse() throws Exception {

        mockMvc.perform(get("/api/moims/joined/detail")
                        .queryParam("ended", "false"))
                .andExpect(status().isOk())
                .andDo(document("자신이 참여한 모임 리스트를 완료 여부로 조회 성공",
                                resource(ResourceSnippetParameters.builder()
                                        .tag("모임")
                                        .summary("로그인한 회원이 참여한 모임 리스트 자세한 정보 조회")
                                        .queryParameters(
                                                parameterWithName("ended").description("완료된 모임을 찾을 지 여부 - [true, false]")
                                        )
                                        .responseFields(
                                                fieldWithPath("data[0].moimId").type(NUMBER).description("모임 Id"),
                                                fieldWithPath("data[0].title").type(STRING).description("모임명"),
                                                fieldWithPath("data[0].capacity").type(NUMBER).description("모집 인원"),
                                                fieldWithPath("data[0].currentCount").type(NUMBER).description("현재 인원"),
                                                fieldWithPath("data[0].imageUrl").type(STRING).description("모임 사진 URL"),
                                                fieldWithPath("data[0].moimCategory").type(VARIES).description(String.format("카테고리 - %s",
                                                        EnumDocsUtils.getEnumNames(MoimCategory.class))),
                                                fieldWithPath("data[0].displayStatus").type(VARIES).description(String.format("공개 여부 - %s",
                                                        EnumDocsUtils.getEnumNames(DisplayStatus.class)))
                                        )
                                        .build())
                        ));
    }
}
