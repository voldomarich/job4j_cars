package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;

public class UserUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            var userRepository = new HibernateRepository(sessionFactory);

            var user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            userRepository.create(user);
            System.out.println();

            System.out.println("findAllOrderById");
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            System.out.println();

            System.out.println("findByLikeLogin");
            userRepository.findByLikeLogin("ej")
                    .forEach(System.out::println);
            System.out.println();

            System.out.println("findByLikeLogin");
            userRepository.findByLikeLogin("adm")
                    .forEach(System.out::println);
            System.out.println();

            System.out.println("findById");
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            System.out.println();

            System.out.println("findByLogin");
            userRepository.findByLogin("admin")
                    .ifPresent(System.out::println);
            System.out.println();

            user.setPassword("password");
            userRepository.update(user);
            System.out.println();

            System.out.println("findByIdAfterUpdating");
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);

            userRepository.delete(user.getId());
            System.out.println();

            System.out.println("findAllByIdAfterDeleting");
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            System.out.println();

            var user2 = new User();
            user2.setLogin("login2");
            user2.setPassword("password2");
            userRepository.create(user2);
            System.out.println();

            System.out.println("findAllByIdAfterRecreating");
            userRepository.findAllOrderById()
                    .forEach(System.out::println);

        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
