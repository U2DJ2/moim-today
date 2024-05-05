package moim_today.presentation.moim;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.moim.moim.MoimService;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimAppendRequest;
import moim_today.dto.moim.moim.MoimDeleteRequest;
import moim_today.dto.moim.moim.MoimMemberDeleteRequest;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.dto.moim.moim_notice.MoimNoticeCreateRequest;
import moim_today.fake_class.moim.FakeMoimNoticeService;
import moim_today.fake_class.moim.FakeMoimService;
import moim_today.util.ControllerTest;
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
    private final FakeMoimNoticeService fakeMoimNoticeService = new FakeMoimNoticeService();

    @Override
    protected Object initController() {
        return new MoimController(fakeMoimService, fakeMoimNoticeService);
    }

    @DisplayName("모임을 생성한다.")
    @Test
    void createPrivateMoimApiTest() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);

        MoimAppendRequest moimAppendRequest = new MoimAppendRequest(
                TITLE.value(),
                CONTENTS.value(),
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
                        .content(objectMapper.writeValueAsString(moimAppendRequest))
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
                                        fieldWithPath("moimCategory").type(VARIES).description("카테고리"),
                                        fieldWithPath("displayStatus").type(VARIES).description("공개 여부"),
                                        fieldWithPath("startDate").type(STRING).description("시작 일자"),
                                        fieldWithPath("endDate").type(STRING).description("종료 일자")
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

        mockMvc.perform(get("/api/moims/detail")
                        .param("moimId", "1")
                )
                .andExpect(status().isOk())
                .andDo(document("모임 정보 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 정보 조회")
                                .queryParameters(
                                        parameterWithName("moimId").description("모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("title").type(STRING).description("모임명"),
                                        fieldWithPath("contents").type(STRING).description("내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("currentCount").type(NUMBER).description("현재 인원"),
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL"),
                                        fieldWithPath("moimCategory").type(VARIES).description("카테고리"),
                                        fieldWithPath("displayStatus").type(VARIES).description("공개여부"),
                                        fieldWithPath("views").type(NUMBER).description("조회수"),
                                        fieldWithPath("startDate").type(STRING).description("시작 일자"),
                                        fieldWithPath("endDate").type(STRING).description("종료 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 정보를 수정한다.")
    @Test
    void updateMoimTest() throws Exception {
        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .moimId(Long.parseLong(MOIM_ID.value()))
                .title(TITLE.value())
                .contents(CONTENTS.value())
                .capacity(Integer.parseInt(CAPACITY.value()))
                .imageUrl(MOIM_IMAGE_URL.value())
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(LocalDate.of(2024,3,1))
                .endDate(LocalDate.of(2024,6,30))
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
                                        fieldWithPath("moimCategory").type(VARIES).description("수정한 카테고리"),
                                        fieldWithPath("displayStatus").type(VARIES).description("수정한 공개여부"),
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
        mockMvc.perform(get("/api/moims/members")
                        .param("moimId", MEMBER_ID.value()))
                .andExpect(status().isOk())
                .andDo(document("모임 멤버 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 조회 성공")
                                .queryParameters(
                                        parameterWithName("moimId").description("모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("isHostRequest").type(BOOLEAN).description("호스트의 요청 여부"),
                                        fieldWithPath("moimMembers[].isHost").type(BOOLEAN).description("해당 멤버가 호스트인지 여부"),
                                        fieldWithPath("moimMembers[].memberId").type(NUMBER).description("멤버 ID"),
                                        fieldWithPath("moimMembers[].memberName").type(STRING).description("멤버 이름"),
                                        fieldWithPath("moimMembers[].joinedDate").type(STRING).description("참여 날짜")
                                                .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .build()
                        )));
    }

    @DisplayName("모임에서 멤버를 추방시킨다")
    @Test
    void deleteMoimMemberTest() throws Exception {
        MoimMemberDeleteRequest moimMemberDeleteRequest = MoimMemberDeleteRequest.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
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
                                        fieldWithPath("moimId").type(NUMBER).description("추방이 일어날 모임 ID"),
                                        fieldWithPath("memberId").type(NUMBER).description("추방시킬 멤버 ID")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임에서 멤버를 추방시킬 때 호스트가 아닌 경우 에러가 발생한다")
    @Test
    void deleteMoimMemberNotHostTest() throws Exception {
        MoimMemberDeleteRequest moimMemberDeleteRequest = MoimMemberDeleteRequest.builder()
                .memberId(MEMBER_ID.longValue() + 1L)
                .moimId(MOIM_ID.longValue())
                .build();

        mockMvc.perform(delete("/api/moims/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimMemberDeleteRequest)))
                .andExpect(status().isForbidden())
                .andDo(document("모임에서 호스트가 아닐 경우 멤버 삭제 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임에서 멤버 삭제")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("추방이 일어날 모임 ID"),
                                        fieldWithPath("memberId").type(NUMBER).description("추방시킬 멤버 ID")
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
                TITLE.value(),
                CONTENTS.value());

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
}
