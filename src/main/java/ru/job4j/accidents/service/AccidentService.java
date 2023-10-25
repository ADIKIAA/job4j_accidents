package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentRepository;

    public Accident save(Accident accident) {
        return accidentRepository.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentRepository.save(accident) != null;
    }

    public void delete(int id) {
        accidentRepository.deleteById(id);
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public Collection<Accident> findAll() {
        return (Collection<Accident>) accidentRepository.findAll();
    }

}
