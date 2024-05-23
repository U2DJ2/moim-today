package moim_today.domain.schedule.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class AvailableColorHexTest {

    @DisplayName("인덱스에 따른 각각의 색상 정보를 반환한다.")
    @Test
    void getHexByCount() {
        // when
        String hexByCount0 = AvailableColorHex.getHexByCount(0);
        String hexByCount1 = AvailableColorHex.getHexByCount(1);
        String hexByCount2 = AvailableColorHex.getHexByCount(2);
        String hexByCount3 = AvailableColorHex.getHexByCount(3);
        String hexByCount4 = AvailableColorHex.getHexByCount(4);
        String hexByCount5 = AvailableColorHex.getHexByCount(5);
        String hexByCount6 = AvailableColorHex.getHexByCount(6);
        String hexByCount7 = AvailableColorHex.getHexByCount(7);
        String hexByCount8 = AvailableColorHex.getHexByCount(8);
        String hexByCount9 = AvailableColorHex.getHexByCount(9);
        String hexByCount10 = AvailableColorHex.getHexByCount(10);

        // then
        assertThat(hexByCount0).isEqualTo(AvailableColorHex.WHITE.colorHex());
        assertThat(hexByCount1).isEqualTo(AvailableColorHex.VERY_LIGHT_RED.colorHex());
        assertThat(hexByCount2).isEqualTo(AvailableColorHex.LIGHT_RED.colorHex());
        assertThat(hexByCount3).isEqualTo(AvailableColorHex.PALE_RED.colorHex());
        assertThat(hexByCount4).isEqualTo(AvailableColorHex.SALMON.colorHex());
        assertThat(hexByCount5).isEqualTo(AvailableColorHex.CORAL.colorHex());
        assertThat(hexByCount6).isEqualTo(AvailableColorHex.BRIGHT_RED.colorHex());
        assertThat(hexByCount7).isEqualTo(AvailableColorHex.CRIMSON.colorHex());
        assertThat(hexByCount8).isEqualTo(AvailableColorHex.DARK_RED.colorHex());
        assertThat(hexByCount9).isEqualTo(AvailableColorHex.DEEP_RED.colorHex());
        assertThat(hexByCount10).isEqualTo(AvailableColorHex.VERY_DARK_RED.colorHex());
    }

    @DisplayName("인덱스가 색상의 개수인 10을 넘어가면 가장 마지막 색상을 반환한다.")
    @Test
    void getHexByCountOverIndex() {
        // when
        String hexByCount11 = AvailableColorHex.getHexByCount(11);
        String hexByCount12 = AvailableColorHex.getHexByCount(12);
        String hexByCount13 = AvailableColorHex.getHexByCount(13);

        // then
        assertThat(hexByCount11).isEqualTo(AvailableColorHex.VERY_DARK_RED.colorHex());
        assertThat(hexByCount12).isEqualTo(AvailableColorHex.VERY_DARK_RED.colorHex());
        assertThat(hexByCount13).isEqualTo(AvailableColorHex.VERY_DARK_RED.colorHex());
    }
}