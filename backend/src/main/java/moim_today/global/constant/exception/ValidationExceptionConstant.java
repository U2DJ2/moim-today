package moim_today.global.constant.exception;

public class ValidationExceptionConstant {
    //회원
    public static final String MEMBER_ID_MIN_ERROR = "잘못된 회원 ID 값이 입력 되었습니다.";
    public static final String MEMBER_PASSWORD_BLANK_ERROR = "비밀번호는 공백일 수 없습니다.";
    public static final String USERNAME_BLANK_ERROR = "사용자 이름은 공백일 수 없습니다.";
    public static final String STUDENT_ID_BLANK_ERROR = "학번은 공백일 수 없습니다.";
    public static final String BIRTH_DATE_NULL_ERROR = "생일은 필수 입력 항목 입니다.";
    public static final String GENDER_NULL_ERROR = "성별은 필수 입력 항목 입니다.";
    public static final String PROFILE_IMAGE_URL_BLANK_ERROR = "프로필 사진 URL은 공백일 수 없습니다.";

    //모임
    public static final String MOIM_ID_MIN_ERROR = "잘못된 모임 ID 값이 입력 되었습니다.";
    public static final String MOIM_TITLE_BLANK_ERROR = "모임 제목은 공백일 수 없습니다.";
    public static final String MOIM_CONTENT_BLANK_ERROR = "모임 내용은 공백일 수 없습니다.";
    public static final String MOIM_CAPACITY_MIN_ERROR = "모임 정원은 2명 이상이어야 합니다.";
    public static final String MOIM_CATEGORY_NULL_ERROR = "모임 카테고리는 필수 입력 항목입니다.";
    public static final String MOIM_START_DATE_NULL_ERROR = "모임 시간 일자는 필수 입력 항목입니다.";
    public static final String MOIM_END_DATE_NULL_ERROR = "모임 종료 일자는 필수 입력 항목";

    //대학
    public static final String UNIVERSITY_ID_MIN_ERROR = "잘못된 대학 ID 값이 입력 되었습니다.";
    
    //어드민
    public static final String ADMIN_PASSWORD_BLANK_ERROR = "관리자 비밀번호는 공백일 수 없습니다.";
    public static final String USER_INQUIRY_ID_MIN_ERROR = "잘못된 문의 ID 값이 입력 되었습니다.";
    public static final String INQUIRY_TITLE_BLANK_ERROR = "제목은 공백일 수 없습니다.";
    public static final String INQUIRY_CONTENT_BLANK_ERROR = "답변은 공백일 수 없습니다.";
    public static final String USER_INQUIRY_RESPONSE_CONTENT_BLANK_ERROR = "답변 내용은 공백일 수 없습니다.";

    //이메일
    public static final String EMAIL_BLANK_ERROR = "이메일은 공백일 수 없습니다.";
    public static final String EMAIL_INVALID_ERROR = "이메일 형식이 올바르지 않습니다.";

    //학과
    public static final String DEPARTMENT_ID_MIN_ERROR = "잘못된 학과 ID 값이 입력 되었습니다.";
    public static final String DEPARTMENT_NAME_BLANK_ERROR = "학과 이름은 공백일 수 없습니다.";

    //파일
    public static final String UPLOAD_FILE_PATH_BLANK_ERROR = "업로드 파일 경로는 공백일 수 없습니다.";
    public static final String FILENAME_BLANK_ERROR = "파일 이름은 공백일 수 없습니다.";

    //미팅
    public static final String MEETING_ID_MIN_ERROR = "잘못된 미팅 ID 값이 입력 되었습니다.";
    public static final String MEETING_AGENDA_BLANK_ERROR = "미팅 의제는 공백일 수 없습니다.";
    public static final String MEETING_START_DATETIME_NULL_ERROR = "미팅 시작 날짜와 시간은 필수 입력 항목입니다.";
    public static final String MEETING_END_DATETIME_NULL_ERROR = "미팅 종료 날짜와 시간은 필수 입력 항목입니다.";
    public static final String MEETING_PLACE_BLANK_ERROR = "미팅 장소는 공백일 수 없습니다.";
    public static final String MEETING_CATEGORY_NULL_ERROR = "미팅 카테고리는 필수 입력 항목입니다.";

    //모임 공지
    public static final String MOIM_NOTICE_ID_MIN_ERROR = "잘못된 모임 공지 ID 값이 입력 되었습니다.";
    public static final String MOIM_NOTICE_TITLE_BLANK_ERROR = "공지 제목은 공백일 수 없습니다.";
    public static final String MOIM_NOTICE_CONTENT_BLANK_ERROR = "공지 내용은 공백일 수 없습니다.";

    //스케줄
    public static final String SCHEDULER_ID_MIN_ERROR = "잘못된 스케줄 ID 값이 입력 되었습니다.";
    public static final String SCHEDULE_NAME_BLANK_ERROR = "스케줄 이름은 공백일 수 없습니다.";
    public static final String SCHEDULE_START_DATE_TIME_NULL_ERROR = "스케줄 시작 일자는 필수 입력 항목입니다.";
    public static final String SCHEDULE_END_DATE_TIME_NULL_ERROR = "스케줄 종료 일자는 필수 입력 항목입니다.";

    //에브리타임
    public static final String EVERY_TIME_START_DATE_NULL_ERROR = "시간표 추가의 시작 일자는 필수 입력 항목입니다.";
    public static final String EVERY_TIME_END_DATE_NULL_ERROR = "시간표 추가의 종료 일자는 필수 입력 항목입니다.";

    //할 일
    public static final String TODO_ID_MIN_ERROR = "잘못된 할 일 ID 값이 입력 되었습니다.";
    public static final String TODO_CONTENT_BLANK_ERROR = "할 일 내용은 공백일 수 없습니다.";
    public static final String TODO_DATE_NULL_ERROR = "할 일 마감 일자는 필수 입력 항목입니다.";
    public static final String TODO_PROGRESS_NULL_ERROR = "할 일 진행도는 필수 입력 항목입니다.";

    //미팅 댓글
    public static final String MEETING_COMMENT_ID_MIN_ERROR = "잘못된 미팅 댓글 ID 값이 입력 되었습니다.";
    public static final String MEETING_COMMENT_CONTENT_BLANK_ERROR = "미팅 내용은 공백일 수 없습니다.";

    //비밀번호 토큰
    public static final String PASSWORD_TOKEN_BLANK_ERROR = "비밀번호 토큰은 공백일 수 없습니다.";
    public static final String NEW_PASSWORD_BLANK_ERROR = "새로운 비밀번호는 공백일 수 없습니다.";
}
