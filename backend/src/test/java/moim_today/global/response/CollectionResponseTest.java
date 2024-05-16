package moim_today.global.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static moim_today.util.TestConstant.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionResponseTest {
    
    @DisplayName("CollectionResponse에 데이터가 올바르게 들어가는지 확인합니다.")
    @Test
    void ApiResponse_테스트(){
        //given
        ArrayList<Member> members = new ArrayList<>();
        Member memberA = new Member(USERNAME.value(), 25);
        Member memberB = new Member(USERNAME.value(), 30);
        members.add(memberA);
        members.add(memberB);

        //when
        CollectionResponse<ArrayList<Member>> arrayListCollectionResponse = CollectionResponse.from(members);

        //then
        assertThat(arrayListCollectionResponse.data()).contains(memberA, memberB);
    }

    static class Member{
        String name;
        int age;

        public Member(final String name, final int age) {
            this.name = name;
            this.age = age;
        }
    }
}