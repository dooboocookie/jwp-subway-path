package subway.repository;

import org.springframework.stereotype.Repository;
import subway.dao.SectionDao;
import subway.dao.entity.SectionEntity;
import subway.domain.Line;
import subway.domain.Section;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SectionRepository {

    private final SectionDao sectionDao;

    public SectionRepository(final SectionDao sectionDao) {
        this.sectionDao = sectionDao;
    }

    public void updateByLine(final Line beforeLine, final Line updateLine) {
        List<Section> beforeSections = beforeLine.getSections();
        beforeSections.removeAll(updateLine.getSections());
        List<SectionEntity> beforeSectionEntities = convertToSectionEntities(beforeLine, beforeSections);
        sectionDao.delete(beforeSectionEntities);

        List<Section> updateSections = updateLine.getSections();
        updateSections.removeAll(beforeLine.getSections());
        List<SectionEntity> updateSectionEntities = convertToSectionEntities(updateLine, updateSections);
        sectionDao.save(updateSectionEntities);
    }

    private List<SectionEntity> convertToSectionEntities(final Line line, final List<Section> sections) {
        return sections.stream()
                .map(section -> SectionEntity.of(section, line))
                .collect(Collectors.toList());
    }
}
