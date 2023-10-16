package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccidentService {

    private final AccidentMem accidentMem = new AccidentMem();

    public Accident save(Accident accident) {
        return accidentMem.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentMem.update(accident);
    }

    public void delete(int id) {
        accidentMem.delete(id);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

}
