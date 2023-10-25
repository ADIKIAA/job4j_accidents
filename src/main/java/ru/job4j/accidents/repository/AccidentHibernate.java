package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class AccidentHibernate {

    private final CrudRepository crudRepository;

    public Accident save(Accident accident) {
        crudRepository.run((Consumer<Session>) session -> session.persist(accident));
        return accident;
    }

    public boolean update(Accident accident) {
        return crudRepository.run((Function<Session, Object>) session -> session.merge(accident)) != null;
    }

    public void delete(int id) {
        crudRepository.run(
                "DELETE FROM Accident WHERE id = :id",
                Map.of("id", id));
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "from Accident WHERE id = :id",
                Accident.class,
                Map.of("id", id));
    }

    public List<Accident> findAll() {
        return crudRepository.query("from Accident", Accident.class);
    }

}
