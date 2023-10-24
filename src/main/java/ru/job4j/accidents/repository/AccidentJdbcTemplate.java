package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    public Accident save(Accident accident) {
        jdbc.update("""
                        INSERT INTO accidents (name, text, address, type_id)
                        VALUES (?, ?, ?, ?);
                        """,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId());
        var rules = accident.getRules().stream().toList();
        jdbc.batchUpdate("""
                INSERT INTO accidents_rules (accident_id, rule_id)
                VALUES (?, ?)
                """, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, accident.getId());
                ps.setInt(2, rules.get(0).getId());
            }

            @Override
            public int getBatchSize() {
                return rules.size();
            }
        });
        return accident;
    }

    public boolean update(Accident accident) {
        jdbc.update("""
                        UPDATE accidents
                        SET name = ?, text = ?, address = ?, type_id = ?
                        WHERE id = ?;
                        
                        DELETE FROM accidents_rules
                        WHERE accident_id = ?;
                        """,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId(),
                accident.getId());
        var rules = accident.getRules().stream().toList();
        return jdbc.batchUpdate("""
                INSERT INTO accidents_rules (accident_id, rule_id)
                VALUES (?, ?)
                """, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, accident.getId());
                ps.setInt(2, rules.get(0).getId());
            }

            @Override
            public int getBatchSize() {
                return rules.size();
            }
        }).length > 0;
    }

    public void delete(int id) {
        jdbc.update("""
                DELETE FROM accidents
                WHERE id = ?;
                
                DELETE FROM accidents_rules
                WHERE accident_id = ?;
                        """,
                id, id);
    }

    public Optional<Accident> findById(int id) {
        var rsl = jdbc.query("""
                select a.id, a.name, text, address, type_id, at.name type_name, r.id rule_id, r.name rule_name from accidents a
                left join accident_types at on a.type_id = at.id
                left join accidents_rules ar on a.id = ar.accident_id
                left join rules r on r.id = ar.rule_id
                where a.id = ?
                """, new AccidentResultSetExtractor(), id);
        return Optional.ofNullable(rsl.get(id));
    }

    public List<Accident> findAll() {
        return jdbc.query("""
                select a.id, a.name, text, address, type_id, at.name type_name, r.id rule_id, r.name rule_name from accidents a
                left join accident_types at on a.type_id = at.id
                left join accidents_rules ar on a.id = ar.accident_id
                left join rules r on r.id = ar.rule_id
                """, new AccidentResultSetExtractor())
                .values().stream().toList();
    }

}
