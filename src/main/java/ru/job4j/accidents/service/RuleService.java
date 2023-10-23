package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.Collection;
import java.util.Optional;

@Service
public class RuleService {

    private final RuleMem ruleMem = new RuleMem();

    public Rule save(Rule rule) {
        return ruleMem.save(rule);
    }

    public void delete(int id) {
        ruleMem.delete(id);
    }

    public Optional<Rule> findById(int id) {
        return ruleMem.findById(id);
    }

    public Collection<Rule> findAll() {
        return ruleMem.findAll();
    }

}
