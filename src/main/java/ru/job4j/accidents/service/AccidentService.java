package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentHibernate;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentHibernate accidentRepository;

    public Accident save(Accident accident) {
        return accidentRepository.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    public void delete(int id) {
        accidentRepository.delete(id);
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

}
