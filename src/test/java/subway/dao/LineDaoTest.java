package subway.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import subway.dao.entity.LineEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class LineDaoTest {

    JdbcTemplate jdbcTemplate;
    LineDao lineDao;

    @Autowired
    LineDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.lineDao = new LineDao(jdbcTemplate);
    }

    @DisplayName("저장한다")
    @Test
    void 저장한다() {
        //given
        String name = "테스트이름";
        String color = "테스트색";
        LineEntity lineEntity = new LineEntity(null, name, color);
        //when
        Long save = lineDao.save(lineEntity);
        //then
        Long excepted = jdbcTemplate.queryForObject("SELECT id FROM lines WHERE name = \'" + name + "\' AND color =  \'" + color + "\'", Long.class);
        assertThat(save).isEqualTo(excepted);
    }

    @DisplayName("모든 노선을 찾는다")
    @Test
    void 모든_노선을_찾는다() {
        //given
        List<Long> ids = jdbcTemplate.queryForList("SELECT id FROM lines", Long.class);
        //when
        List<LineEntity> all = lineDao.findAll();
        //then
        assertThat(all).hasSize(ids.size());
    }

    @DisplayName("아이디로 노선을 찾는다")
    @Test
    void 아이디로_노선을_찾는다() {
        //given
        jdbcTemplate.update("INSERT INTO lines (id, name, color) VALUES (1, \'테스트이름\', \'테스트색\')");
        //when
        LineEntity lineEntity = lineDao.findById(1L).get();
        //then
        assertThat(lineEntity).isEqualTo(new LineEntity(1L, "테스트이름", "테스트색"));
    }

    @DisplayName("아이디를 통해 수정한다.")
    @Test
    void 수정한다() {
        //given
        String expected = "수정";
        jdbcTemplate.update("INSERT INTO lines (id, name, color) VALUES (1, \'테스트이름\', \'테스트색\')");
        LineEntity lineEntity = new LineEntity(1L, expected, "테스트색");
        //when
        lineDao.update(lineEntity);
        //then
        String editName = jdbcTemplate.queryForObject("SELECT name FROM lines WHERE id = 1", String.class);
        assertThat(editName).isEqualTo(expected);
    }

    @DisplayName("삭제한다.")
    @Test
    void 삭제한다() {
        //given
        jdbcTemplate.update("INSERT INTO lines (id, name, color) VALUES (1, \'테스트이름\', \'테스트색\')");
        LineEntity lineEntity = new LineEntity(1L, "테스트이름", "테스트색");
        //when
        lineDao.delete(lineEntity);
        //then
        List<Map<String, Object>> empty = jdbcTemplate.queryForList("SELECT name FROM lines WHERE id = 1");
        assertThat(empty).isEmpty();
    }
}
