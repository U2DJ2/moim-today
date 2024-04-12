package moim_today.global.constant;

public enum UniversityConstant {

    ASSOCIATE_DEGREE("전문대학"),
    GRADUATE_DEGREE("대학교"),
    DATA_SEARCH("dataSearch"),
    CONTENT("content"),
    MAJOR_SEQ("majorSeq"),
    LINK("link"),
    SCHOOL_NAME("schoolName"),
    SCHOOL_TYPE("schoolType"),
    UNIVERSITY("UNIVERSITY"),
    MAJOR_NAME("majorName"),
    UNIVERSITY_API_URL("http://www.career.go.kr/cnet/openapi/getOpenApi?apiKey="),
    FETCH_ALL_UNIVERSITY_URL("&contentType=json&svcType=api&svcCode=SCHOOL&gubun=univ_list&perPage=475"),
    FETCH_ALL_DEPARTMENT_URL("&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=대학&perPage=1000"),
    FETCH_ALL_UNIVERSITY_BY_DEPARTMENT_URL("&svcType=api&svcCode=MAJOR_VIEW&contentType=json&gubun=univ_list&univSe=대학&perPage=1000&majorSeq=");

    private final String value;

    UniversityConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
