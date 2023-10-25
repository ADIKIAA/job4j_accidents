package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    public Rule save(Rule rule) {
        return ruleRepository.save(rule);
    }

    public void delete(int id) {
        ruleRepository.deleteById(id);
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    public Collection<Rule> findAll() {
        return (Collection<Rule>) ruleRepository.findAll();
    }

}
