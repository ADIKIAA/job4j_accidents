package ru.job4j.accidents.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccidentResultSetExtractor implements ResultSetExtractor<Map<Integer, Accident>> {

    @Override
    public Map<Integer, Accident> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Accident> rsl = new HashMap<>();
        Accident accident;
        int accidentId;
        while (rs.next()) {
            accidentId = rs.getInt("id");
            if (!rsl.containsKey(accidentId)) {
                accident = new Accident();
                accident.setId(accidentId);
                accident.setName(rs.getString("name"));
                accident.setText(rs.getString("text"));
                accident.setAddress(rs.getString("address"));

                AccidentType accidentType = new AccidentType();
                accidentType.setId(rs.getInt("type_id"));
                accidentType.setName(rs.getString("type_name"));

                accident.setType(accidentType);

                rsl.put(accidentId, accident);
            }

            Rule rule = new Rule();
            rule.setId(rs.getInt("rule_id"));
            rule.setName(rs.getString("rule_name"));

            rsl.get(accidentId).addRule(rule);
        }
        return rsl;
    }

}
