package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Image;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest {

    private static SessionFactory sessionFactory;
    private Session session;
    private PostRepository postRepository;

    @BeforeAll
    static void setUpFactory() {
        sessionFactory = new Configuration()
                .configure("hibernate_test.cfg.xml")
                .buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        session = sessionFactory.openSession();
        CrudRepository crudRepository = new CrudRepository(sessionFactory);
        postRepository = new PostRepository(crudRepository);

        session.beginTransaction();

        User user = new User();
        user.setLogin("Tester");
        session.persist(user);

        Car toyota = new Car();
        toyota.setBrand("Toyota");
        toyota.setUser(user);
        session.persist(toyota);

        Car bmw = new Car();
        bmw.setBrand("BMW");
        bmw.setUser(user);
        session.persist(bmw);

        Post recent = new Post();
        recent.setDescription("Recent Toyota with photo");
        recent.setCreated(LocalDateTime.now());
        recent.setPhoto(new Image());
        recent.setUser(user);
        recent.setCar(toyota);
        session.persist(recent);

        Post old = new Post();
        old.setDescription("Old BMW without photo");
        old.setCreated(LocalDateTime.now().minusDays(1));
        old.setUser(user);
        old.setCar(bmw);
        session.persist(old);

        session.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (session != null) {
            session.close();
        }
    }

    @AfterAll
    static void tearDownFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void testFindPostsFromLastDay() {
        List<Post> posts = postRepository.findPostsFromLastDay();
        assertEquals(1, posts.size());
        assertTrue(posts.get(0).getDescription().contains("Recent"));
    }

    @Test
    void testFindPostsWithPhoto() {
        List<Post> posts = postRepository.findPostsWithPhoto();
        assertEquals(1, posts.size());
        assertNotNull(posts.get(0).getPhoto());
    }

    @Test
    void testFindPostsByCarName() {
        List<Post> toyotaPosts = postRepository.findPostsByCarBrand("Toyota");
        assertEquals(1, toyotaPosts.size());

        List<Post> bmwPosts = postRepository.findPostsByCarBrand("BMW");
        assertEquals(1, bmwPosts.size());
    }

    @Test
    void testCreate() {
        User user = session.get(User.class, 1);
        Car car = session.get(Car.class, 1);

        Post newPost = new Post();
        newPost.setDescription("New post");
        newPost.setCreated(LocalDateTime.now());
        newPost.setUser(user);
        newPost.setCar(car);

        Post savedPost = postRepository.create(newPost);
        assertNotNull(savedPost);
        assertTrue(savedPost.getId() > 0);

        Post found = session.get(Post.class, savedPost.getId());
        assertEquals("New post", found.getDescription());
    }

    @Test
    void testUpdate() {
        Post post = postRepository.findAll().get(0);
        post.setDescription("Updated description");
        postRepository.update(post);

        Post updated = session.get(Post.class, post.getId());
        assertEquals("Updated description", updated.getDescription());
    }

    @Test
    void testDelete() {
        Post post = postRepository.findAll().get(0);
        int id = post.getId();
        postRepository.delete(id);
        Post deleted = session.get(Post.class, id);
        assertNull(deleted);
    }

    @Test
    void testFindById() {
        Post post = postRepository.findAll().get(0);
        Optional<Post> result = postRepository.findById(post.getId());
        assertTrue(result.isPresent());
        assertEquals(post.getId(), result.get().getId());
    }

    @Test
    void testFindAll() {
        List<Post> posts = postRepository.findAll();
        assertEquals(2, posts.size());
    }
}
