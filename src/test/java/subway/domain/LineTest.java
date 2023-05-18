package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LineTest {

    private final Section SECTION = new Section(new Station("잠실나루"), new Station("잠실새내"), new Distance(10));

    @DisplayName("생성한다")
    @Test
    void 생성한다() {
        assertDoesNotThrow(() ->
                new Line(
                        new LineName("2호선"),
                        new LineColor("초록"),
                        new Sections(List.of(SECTION))));
    }

    @DisplayName("노선명이 null이면 예외를 발생한다")
    @Test
    void 노선명이_NULL이면_예외를_발생한다() {
        assertThatThrownBy(() ->
                new Line(
                        null,
                        new LineColor("초록"),
                        new Sections(List.of(SECTION))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("노선색이 null이면 예외를 발생한다")
    @Test
    void 노선색이_NULL이면_예외를_발생한다() {
        assertThatThrownBy(() ->
                new Line(
                        new LineName("2호선"),
                        null,
                        new Sections(List.of(SECTION))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("노선의 구간이 null이면 예외를 발생한다")
    @Test
    void 노선의_구간이_NULL이면_예외를_발생한다() {
        assertThatThrownBy(() ->
                new Line(
                        new LineName("2호선"),
                        new LineColor("초록"),
                        null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("역이 중복된 이름이 있는지 검증한다")
    @Test
    void 역이_이미_있는지_검증한다() {
        //given
        Line line = new Line(new LineName("2호선"), new LineColor("빨강"), new Sections(List.of(SECTION)));
        Station jamsilnaru = new Station("잠실나루");
        //then
        assertThatThrownBy(() -> line.validateNotDuplicatedStation(jamsilnaru)).isInstanceOf(IllegalArgumentException.class);
    }
}
