package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HibernateRepository implements UserRepository {

    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery(
                            "UPDATE User SET login = :fLogin WHERE id = :fId")
                    .setParameter("fLogin", "new login")
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> result = new ArrayList<>();
        try {
            result = session.createQuery("FROM User order by id ASC", User.class)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<User> result = Optional.empty();
        try {
            Query<User> query = session.createQuery(
                    "from User as i where i.id = :fId", User.class);
            query.setParameter("fId", userId);
            result = query.uniqueResultOptional();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> result = new ArrayList<>();
        try {
            Query<User> query = session.createQuery(
                    "from User as i where i.login = :fLogin", User.class);
            query.setParameter("fLogin", "%" + key + "%");
            result = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Optional<User> result = Optional.empty();
        try {
            Query<User> query = session.createQuery(
                    "from User as i where i.login = :fLogin", User.class);
            query.setParameter("fLogin", login);
            result = query.uniqueResultOptional();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }
}
