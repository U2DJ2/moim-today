package moim_today.global.constant;

public enum UniversityConstant {

    ASSOCIATE_DEGREE("전문대학"),
    GRADUATE_DEGREE("대학교"),
    DATA_SEARCH(""),
    UNIVERSITY_API_URL("http://www.career.go.kr/cnet/openapi/getOpenApi?apiKey="),
    FETCH_ALL_UNIVERSITY_URL("&contentType=json&svcType=api&svcCode=SCHOOL&gubun=univ_list&perPage=475");

    private final String value;

    UniversityConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
