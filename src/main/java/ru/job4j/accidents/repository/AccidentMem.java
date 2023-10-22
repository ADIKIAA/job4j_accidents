package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        save(new Accident(0, "Авария", "Наезд на ограждение", "ул. Новая д.23",
                new AccidentType(1, "Две машины"),
                Set.of(new Rule(1, "Статья 1."))));
    }

    public Accident save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public boolean update(Accident accident) {
        return accidents.computeIfPresent(
                accident.getId(),
                (id, oldAccident) -> new Accident(oldAccident.getId(),
                        accident.getName(),
                        accident.getText(),
                        accident.getAddress(),
                        accident.getType(),
                        accident.getRules())
        ) != null;
    }

    public void delete(int id) {
        accidents.remove(id);
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }

}
