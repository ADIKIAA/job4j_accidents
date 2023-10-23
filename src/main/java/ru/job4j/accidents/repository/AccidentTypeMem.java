package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();

    public AccidentTypeMem() {
        save(new AccidentType(0, "Две машины"));
        save(new AccidentType(0, "Машина и человек"));
        save(new AccidentType(0, "Машина и велосипед"));
    }

    public AccidentType save(AccidentType accidentType) {
        accidentType.setId(nextId.incrementAndGet());
        accidentTypes.put(accidentType.getId(), accidentType);
        return accidentType;
    }

    public void delete(int id) {
        accidentTypes.remove(id);
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    public Collection<AccidentType> findAll() {
        return accidentTypes.values();
    }

}
