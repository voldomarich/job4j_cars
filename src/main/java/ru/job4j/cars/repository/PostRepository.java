package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    public List<Post> findPostsFromLastDay() {
        String hql = "FROM Post p WHERE p.created > :yesterday";
        Map<String, Object> params = Map.of(
                "yesterday", LocalDateTime.now().minusDays(1)
        );
        return crudRepository.query(hql, Post.class, params);
    }

    public List<Post> findPostsWithPhoto() {
        return crudRepository.tx(session -> {
            String hql = "SELECT DISTINCT p FROM Post p JOIN p.photos ph";
            return session.createQuery(hql, Post.class).getResultList();
        });
    }

    public List<Post> findPostsByCarBrand(String brand) {
        String hql = "FROM Post p WHERE p.car.brand = :brand";
        Map<String, Object> params = Map.of("brand", brand);
        return crudRepository.query(hql, Post.class, params);
    }

    public Post create(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    public void delete(int carId) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", carId)
        );
    }

    public Optional<Post> findById(int carId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", carId)
        );
    }

    public List<Post> findAll() {
        return crudRepository.query(
                "from Post", Post.class
        );
    }
}
