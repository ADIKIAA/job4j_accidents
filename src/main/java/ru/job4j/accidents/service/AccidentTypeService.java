package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccidentTypeService {

    private final AccidentTypeMem accidentTypeMem = new AccidentTypeMem();

    public AccidentType save(AccidentType accidentType) {
        return accidentTypeMem.save(accidentType);
    }

    public void delete(int id) {
        accidentTypeMem.delete(id);
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeMem.findById(id);
    }

    public Collection<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }

}
