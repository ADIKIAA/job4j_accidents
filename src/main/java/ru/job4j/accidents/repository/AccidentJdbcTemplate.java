package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    private final Map<Integer, Accident> repository = new ConcurrentHashMap<>();

    public Accident save(Accident accident) {
        jdbc.update("insert into accidents (name, text, address, type, rules) values (?)",
                accident.getName());
        return accident;
    }

    public boolean update(Accident accident) {
        return jdbc.update("update accidents set name = ? where id = ?",
                accident.getName(),
                accident.getId()
        ) > 0;
    }

    public void delete(int id) {
        jdbc.update("delete from accidents where id = ?", id);
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(repository.get(id));
    }

    public List<Accident> findAll() {
        jdbc.query("""
                select a.id, a.name, text, address, type_id, at.name type_name, r.id rule_id, r.name rule_name from accidents a
                left join accident_types at on a.type_id = at.id
                left join accidents_rules ar on a.id = ar.accident_id
                left join rules r on r.id = ar.rule_id
                """, new AccidentRowMapper(repository));
        return repository.values().stream().toList();
    }

}
