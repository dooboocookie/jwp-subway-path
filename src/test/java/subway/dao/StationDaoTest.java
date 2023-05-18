package subway.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import subway.dao.entity.StationEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
class StationDaoTest {

    private JdbcTemplate jdbcTemplate;
    private StationDao stationDao;

    @Autowired
    StationDaoTest (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.stationDao = new StationDao(jdbcTemplate);
    }

    @DisplayName("저장한다")
    @Test
    void 저장한다() {
        //given
        String name = "테스트";
        StationEntity stationEntity = new StationEntity(null, name);
        //when
        Long save = stationDao.save(stationEntity);
        //then
        Long excepted = jdbcTemplate.queryForObject("SELECT id FROM stations WHERE name = \'" + name + "\'", Long.class);
        assertThat(save).isEqualTo(excepted);
    }

    @DisplayName("모든 역을 찾는다")
    @Test
    void 모든_역을_찾는다() {
        //given
        List<Long> ids = jdbcTemplate.queryForList("SELECT id FROM stations", Long.class);
        //when
        List<StationEntity> all = stationDao.findAll();
        //then
        assertThat(all).hasSize(ids.size());
    }

    @DisplayName("아이디로 역을 찾는다")
    @Test
    void 아이디로_역을_찾는다() {
        //given
        jdbcTemplate.update("INSERT INTO stations (id, name) VALUES (1, \'테스트\')");
        //when
        StationEntity station = stationDao.findById(1L).get();
        //then
        assertThat(station).isEqualTo(new StationEntity(1L, "테스트"));
    }

    @DisplayName("아이디를 통해 수정한다.")
    @Test
    void 수정한다() {
        //given
        String expected = "수정";
        jdbcTemplate.update("INSERT INTO stations (id, name) VALUES (1, \'테스트\')");
        StationEntity stationEntity = new StationEntity(1L, expected);
        //when
        stationDao.update(stationEntity);
        //then
        String editName = jdbcTemplate.queryForObject("SELECT name FROM stations WHERE id = 1", String.class);
        assertThat(editName).isEqualTo(expected);
    }

    @DisplayName("아이디를 통해 삭제한다.")
    @Test
    void 삭제한다() {
        //given
        jdbcTemplate.update("INSERT INTO stations (id, name) VALUES (1, \'테스트\')");
        StationEntity stationEntity = new StationEntity(1L, "테스트");
        //when
        stationDao.deleteById(1L);
        //then
        List<Map<String, Object>> empty = jdbcTemplate.queryForList("SELECT name FROM stations WHERE id = 1");
        assertThat(empty).isEmpty();
    }
}
