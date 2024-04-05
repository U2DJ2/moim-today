package moim_today.global.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionResponseTest {
    
    @DisplayName("ApiResponse에 데이터가 올바르게 들어가는지 확인합니다.")
    @Test
    void ApiResponse_테스트(){
        //given
        Member member = new Member("testMember", 25);

        //when
        CollectionResponse<Member> memberCollectionResponse = CollectionResponse.of(member);

        //then
        assertThat(memberCollectionResponse.data()).isEqualTo(member);
        assertThat(memberCollectionResponse.data().getName()).isEqualTo("testMember");
        assertThat(memberCollectionResponse.data().getAge()).isEqualTo(25);
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