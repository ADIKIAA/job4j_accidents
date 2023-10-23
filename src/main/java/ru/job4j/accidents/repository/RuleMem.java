package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleMem {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public RuleMem() {
        save(new Rule(0, "Статья. 1"));
        save(new Rule(0, "Статья. 2"));
        save(new Rule(0, "Статья. 3"));
    }

    public Rule save(Rule rule) {
        rule.setId(nextId.incrementAndGet());
        rules.put(rule.getId(), rule);
        return rule;
    }

    public void delete(int id) {
        rules.remove(id);
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    public Collection<Rule> findAll() {
        return rules.values();
    }

}
