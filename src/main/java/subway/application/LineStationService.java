package subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.domain.Distance;
import subway.domain.Line;
import subway.domain.Section;
import subway.domain.Station;
import subway.dto.LineStationResponse;
import subway.dto.LineStationsRequest;
import subway.repository.LineRepository;
import subway.repository.SectionRepository;
import subway.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class LineStationService {

    private final LineRepository lineRepository;
    private final SectionRepository sectionRepository;
    private final StationRepository stationRepository;

    public LineStationService(final LineRepository lineRepository, final SectionRepository sectionRepository, final StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.sectionRepository = sectionRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public void saveLinesStations(final Long lineId, final LineStationsRequest request) {
        Line line = lineRepository.findById(lineId);
        Station upStation = stationRepository.findById(request.getUpStationId());
        Station downStation = stationRepository.findById(request.getDownStationId());
        Distance distance = new Distance(request.getDistance());
        Section section = new Section(upStation, downStation, distance);
        Line updateLine = line.addSection(section);
        sectionRepository.updateByLine(line, updateLine);
    }

    public List<LineStationResponse> findAllLinesStations() {
        final List<Line> lines = lineRepository.findAll();

        List<LineStationResponse> lineStationResponses = new ArrayList<>();
        for (Line line : lines) {
            List<Station> stations = line.findAllStation();
            lineStationResponses.add(LineStationResponse.of(line, stations));
        }
        return lineStationResponses;
    }

    public LineStationResponse findLinesStations(final Long lineId) {
        final Line line = lineRepository.findById(lineId);
        List<Station> stations = line.findAllStation();
        return LineStationResponse.of(line, stations);
    }

    @Transactional
    public void deleteLinesStations(final Long lineId, final Long stationId) {
        Line line = lineRepository.findById(lineId);
        Station station = stationRepository.findById(stationId);
        Line updateLine = line.removeStation(station);
        sectionRepository.updateByLine(line, updateLine);
    }
}
