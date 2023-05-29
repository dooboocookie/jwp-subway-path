package subway.dto;

import java.util.List;
import java.util.Objects;

public class PathResponse {

    private Integer distance;
    private Integer charge;
    private List<SectionResponse> paths;

    public PathResponse() {
    }

    public PathResponse(final Integer distance, final Integer charge, final List<SectionResponse> paths) {
        this.distance = distance;
        this.charge = charge;
        this.paths = paths;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getCharge() {
        return charge;
    }

    public List<SectionResponse> getPaths() {
        return paths;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PathResponse that = (PathResponse) o;
        return Objects.equals(distance, that.distance)
                && Objects.equals(charge, that.charge)
                && Objects.equals(paths, that.paths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, charge, paths);
    }

    @Override
    public String toString() {
        return "PathResponse{" +
                "distance=" + distance +
                ", charge=" + charge +
                ", paths=" + paths +
                '}';
    }
}
