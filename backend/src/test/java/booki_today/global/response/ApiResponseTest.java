package booki_today.global.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {
    
    @DisplayName("ApiResponse에 데이터가 올바르게 들어가는지 확인합니다.")
    @Test
    void ApiResponse_테스트(){
        //given
        Member member = new Member("testMember", 25);

        //when
        ApiResponse<Member> memberApiResponse = ApiResponse.of(member);

        //then
        assertThat(memberApiResponse.data()).isEqualTo(member);
        assertThat(memberApiResponse.data().getName()).isEqualTo("testMember");
        assertThat(memberApiResponse.data().getAge()).isEqualTo(25);
    }

    static class Member{
        String name;
        int age;

        public Member(final String name, final int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}