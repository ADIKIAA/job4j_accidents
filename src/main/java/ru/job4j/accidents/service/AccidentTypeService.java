package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    public AccidentType save(AccidentType accidentType) {
        return accidentTypeRepository.save(accidentType);
    }

    public void delete(int id) {
        accidentTypeRepository.deleteById(id);
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    public Collection<AccidentType> findAll() {
        return (Collection<AccidentType>) accidentTypeRepository.findAll();
    }

}
