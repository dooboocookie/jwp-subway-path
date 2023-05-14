package subway.domain;

import java.util.Objects;

public class LineColor {

    private final String value;

    public LineColor(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LineColor lineColor = (LineColor) o;
        return Objects.equals(value, lineColor.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LineColor{" +
                "value='" + value + '\'' +
                '}';
    }
}
