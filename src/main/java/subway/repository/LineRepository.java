package subway.repository;

import org.springframework.stereotype.Repository;
import subway.dao.LineDao;
import subway.dao.SectionDao;
import subway.dao.StationDao;
import subway.dao.entity.LineEntity;
import subway.dao.entity.SectionEntity;
import subway.dao.entity.StationEntity;
import subway.domain.*;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class LineRepository {

    private final StationDao stationDao;
    private final SectionDao sectionDao;
    private final LineDao lineDao;

    public LineRepository(final StationDao stationDao, final SectionDao sectionDao, final LineDao lineDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
        this.lineDao = lineDao;
    }

    public List<Line> findAll() {
        List<LineEntity> allLines = lineDao.findAll();
        Map<Long, Station> allStationsById = stationDao.findAll().stream()
                .map(StationEntity::convertToStation)
                .collect(Collectors.toMap(Station::getId, station -> station));
        Map<Long, List<Section>> allSectionsByLindId = getAllSectionsByLineId(allStationsById);
        return allLines.stream()
                .map(lineEntity -> lineEntity.convertToLine(allSectionsByLindId.getOrDefault(lineEntity.getId(), new ArrayList<>())))
                .collect(Collectors.toList());
    }

    public Line findById(final Long id) {
        LineEntity lineEntity = lineDao.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 라인이 없습니다."));
        Map<Long, Station> allStationsById = stationDao.findAll().stream()
                .map(StationEntity::convertToStation)
                .collect(Collectors.toMap(Station::getId, station -> station));
        Map<Long, List<Section>> allSectionsByLindId = getAllSectionsByLineId(allStationsById);
        return lineEntity.convertToLine(allSectionsByLindId.getOrDefault(lineEntity.getId(), new ArrayList<>()));
    }

    private Map<Long, List<Section>> getAllSectionsByLineId(final Map<Long, Station> allStationsById) {
        Map<Long, List<Section>> allSectionsByLindId = new HashMap<>();
        List<SectionEntity> allSections = sectionDao.findAll();
        for (SectionEntity sectionsEntity : allSections) {
            Long lineId = sectionsEntity.getLineId();
            List<Section> sections = allSectionsByLindId.getOrDefault(lineId, new ArrayList<>());
            sections.add(sectionsEntity.convertToSection(allStationsById));
            allSectionsByLindId.put(lineId, sections);
        }
        return allSectionsByLindId;
    }
}
