package moim_today.domain.schedule.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ColorHexTest {

    @DisplayName("인덱스에 따른 각각의 색상 정보를 반환한다.")
    @Test
    void getHexByCount() {
        // when
        ColorHex hexByCount0 = ColorHex.getHexByCount(0);
        ColorHex hexByCount1 = ColorHex.getHexByCount(1);
        ColorHex hexByCount2 = ColorHex.getHexByCount(2);
        ColorHex hexByCount3 = ColorHex.getHexByCount(3);
        ColorHex hexByCount4 = ColorHex.getHexByCount(4);
        ColorHex hexByCount5 = ColorHex.getHexByCount(5);

        ColorHex hexByCount20 = ColorHex.getHexByCount(19);

        // then
        assertThat(hexByCount0).isEqualTo(ColorHex.RED);
        assertThat(hexByCount1).isEqualTo(ColorHex.GREEN);
        assertThat(hexByCount2).isEqualTo(ColorHex.BLUE);
        assertThat(hexByCount3).isEqualTo(ColorHex.YELLOW);
        assertThat(hexByCount4).isEqualTo(ColorHex.MAGENTA);
        assertThat(hexByCount5).isEqualTo(ColorHex.PURPLE);

        assertThat(hexByCount20).isEqualTo(ColorHex.CORAL);
    }

    @DisplayName("인덱스가 색상의 개수인 20을 넘어가면 처음 인덱스에서 다시 시작한다.")
    @Test
    void getHexByCountOverIndex() {
        // when
        ColorHex hexByCount21 = ColorHex.getHexByCount(20);
        ColorHex hexByCount22 = ColorHex.getHexByCount(21);
        ColorHex hexByCount23 = ColorHex.getHexByCount(22);
        ColorHex hexByCount24 = ColorHex.getHexByCount(23);
        ColorHex hexByCount25 = ColorHex.getHexByCount(24);

        ColorHex hexByCount41 = ColorHex.getHexByCount(40);

        // then
        assertThat(hexByCount21).isEqualTo(ColorHex.RED);
        assertThat(hexByCount22).isEqualTo(ColorHex.GREEN);
        assertThat(hexByCount23).isEqualTo(ColorHex.BLUE);
        assertThat(hexByCount24).isEqualTo(ColorHex.YELLOW);
        assertThat(hexByCount25).isEqualTo(ColorHex.MAGENTA);

        assertThat(hexByCount41).isEqualTo(ColorHex.RED);
    }
}