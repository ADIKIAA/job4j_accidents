package ru.job4j.accidents.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AccidentRowMapper implements RowMapper<Accident> {

    private Map<Integer, Accident> accidents;

    public AccidentRowMapper(Map<Integer, Accident> accidents) {
        this.accidents = accidents;
    }

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getAccidentFromResultSet(rs, accidents);
    }

    /**
     * Метод собирает модель Accident из ResultSet
     * Не содержит Set<Rule>
     *
     * @param rs ResultSet
     * @return Accident
     * @throws SQLException exception
     */
    private Accident getAccidentFromResultSet(ResultSet rs, Map<Integer, Accident> accidents) throws SQLException {

        int accidentId = rs.getInt("id");
        Accident accident = accidents.get(accidentId);

        if (accident == null) {
            accident = new Accident();

            accident.setId(accidentId);
            accident.setName(rs.getString("name"));
            accident.setText(rs.getString("text"));
            accident.setAddress(rs.getString("address"));

            AccidentType accidentType = new AccidentType();

            accidentType.setId(rs.getInt("type_id"));
            accidentType.setName(rs.getString("type_name"));

            accident.setType(accidentType);
        }

        int ruleId = rs.getInt("rule_id");
        setRuleToAccident(rs, accident, ruleId);

        accidents.put(accidentId, accident);

        return accident;
    }

    /**
     * Метод добавляет модель Rule в Accident из общего запроса ResultSet
     *
     * @param ruleId    int Role ID
     * @param rs ResultSet
     * @param accident  Accident
     * @throws SQLException Exception
     */
    private void setRuleToAccident(ResultSet rs, Accident accident, int ruleId) throws SQLException {
        if (ruleId != 0) {
            Rule rule = new Rule();
            rule.setId(ruleId);
            rule.setName(rs.getString("rule_name"));
            accident.addRule(rule);
        }
    }

}
