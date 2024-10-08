package moim_today.util;


public enum TestConstant {

    // 이메일
    EMAIL("email@ajou.ac.kr"),
    AJOU_EMAIL("test@ajou.ac.kr"),
    AJOU_EMAIL_DOMAIN("ajou.ac.kr"),
    WRONG_EMAIL("wrongEmail@ajou.ac.kr"),
    ALREADY_EXIST_EMAIL("alreayExist@ajou.ac.kr"),

    // 비밀번호
    PASSWORD("password"),
    NEW_PASSWORD("newPassword"),
    WRONG_PASSWORD("wrongPassword"),

    // 에브리타임
    EVERY_TIME_ID("everyTimeId"),
    WRONG_EVERY_TIME_ID("wrong everyTimeId"),

    // 인증토큰
    CERTIFICATION_TOKEN("certificationToken"),
    NEW_CERTIFICATION_TOKEN("newCertificationToken"),
    WRONG_CERTIFICATION_TOKEN("wrongCertificationToken"),

    // 파일
    FILE_NAME("file"),
    ORIGINAL_FILE_NAME("file.txt"),
    FILE_CONTENT("content"),


    // 대학교
    UNIVERSITY_ID("universityId"),
    UNIV_ID("194"),
    UNIVERSITY_NAME("universityName"),
    AJOU_UNIVERSITY_NAME("아주대학교"),

    // 학과
    DEPARTMENT_ID("9369"),
    REQUEST_DEPARTMENT_ID("1"),
    DEPARTMENT_NAME("departmentName"),

    // 회원
    MEMBER_ID("1"),
    USERNAME("username"),
    STUDENT_ID("studentId"),
    PROFILE_IMAGE_URL("profileImageUrl"),


    // 모임
    MOIM_ID("1"),
    FORBIDDEN_MOIM_ID("9999"),
    FIRST_CREATED_MOIM_TITLE("first title"),
    SECOND_CREATED_MOIM_TITLE("second title"),
    MOIM_TITLE("moim title"),
    MOIM_CONTENTS("moim contents"),
    CAPACITY("5"),
    MOIM_IMAGE_URL("moimImageUrl"),
    CURRENT_COUNT("0"),
    VIEWS("10"),


    //공지
    NOTICE_ID("1"),
    FORBIDDEN_NOTICE_ID("9999"),
    NOT_FOUND_NOTICE_ID("0"),
    NOTICE_TITLE("notice title"),
    NOTICE_CONTENTS("notice contents"),


    // 스케줄
    SCHEDULE_ID("1"),
    SCHEDULE_NAME("scheduleName"),
    NEW_SCHEDULE_NAME("new scheduleName"),
    FORBIDDEN_SCHEDULE_ID("9999"),
    NOTFOUND_SCHEDULE_ID("0"),

    //미팅
    MEETING_ID("123"),
    NOT_FOUND_MEETING_ID("0"),
    MEETING_AGENDA("meeting agenda"),
    MEETING_PLACE("meeting place"),
    MEETING_COMMENT_ID("456"),
    MEETING_COMMENT_CONTENTS("meeting comment contents"),

    FIRST_CREATED_MEETING_AGENDA("first agenda"),
    SECOND_CREATED_MEETING_AGENDA("second agenda"),

    STATUS_CODE("200"),
    MESSAGE("message"),

    LOCAL_DATE_FORMAT("yyyy-MM-dd"),
    LOCAL_DATE_TIME_FORMAT("yyyy-MM-dd HH:mm:ss"),

    //투두
    TODO_ID("1"),
    UPDATE_BEFORE_CONTENT("업데이트하기 전 Todo Content"),
    UPDATE_AFTER_CONTENT("업데이트한 이후 Todo Content"),

    //어드민
    ADMIN_PASSWORD("admim123"),
    WRONG_ADMIN_PASSWORD("wrong admin password"),
    ADMIN_SESSION_VALUE("admin session json"),

    //서비스 문의
    INQUIRY_TITLE("문의 제목"),
    INQUIRY_CONTENT("문의 내용");

    private final String value;

    TestConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public int intValue(){
        return Integer.parseInt(value);
    }

    public long longValue() {
        return Long.parseLong(value);
    }
}
