package subway.domain;

import java.util.Objects;

public class LineName {

    private final String value;

    public LineName(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("역 이름은 빈 값일 수 없습니다.");
        }
        if (value.length() > 10) {
            throw new IllegalArgumentException("역 이름은 10자 이하로 입력해주세요.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LineName lineName = (LineName) o;
        return Objects.equals(value, lineName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LineName{" +
                "value='" + value + '\'' +
                '}';
    }
}
