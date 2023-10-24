package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {

    private final SessionFactory sf;

    public Accident save(Accident accident) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return accident;
    }

    public boolean update(Accident accident) {
        Session session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery("""
                    UPDATE Accident
                    SET name = :name, text = :text, address = :address, 
                    type = :type, rules = :rules
                    WHERE id = :id
                    """)
                    .setParameter("name", accident.getName())
                    .setParameter("text", accident.getText())
                    .setParameter("address", accident.getAddress())
                    .setParameter("type", accident.getType())
                    .setParameter("rules", accident.getRules())
                    .setParameter("id", accident.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl != 0;
    }

    public void delete(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("""
                    DELETE FROM Accident 
                    WHERE id = :id
                    """)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public Optional<Accident> findById(int id) {
        Session session = sf.openSession();
        Optional<Accident> rsl = Optional.empty();
        try {
            session.beginTransaction();
            rsl = session.createQuery(" from Accident WHERE id = :id", Accident.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    public List<Accident> findAll() {
        Session session = sf.openSession();
        List<Accident> rsl = List.of();
        try {
            session.beginTransaction();
            rsl = session.createQuery("from Accident", Accident.class)
                            .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

}
