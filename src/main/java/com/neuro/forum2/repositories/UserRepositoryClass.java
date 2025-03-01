package com.neuro.forum2.repositories;

import com.neuro.forum2.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class UserRepositoryClass {


        @Qualifier(value = "postgresEntityManager")
        @Autowired
        private EntityManager entityManager;

        public Optional<User> findByUsername(String username) {
            try {
                Query query = entityManager.createNativeQuery("select id, username, password from users where username = :username", User.class);
                User user = (User) query.setParameter("username", username).getSingleResult();
                return Optional.of(user);
            } catch (NoResultException e){
                return Optional.empty();
            }
        }

        public void saveUser(User user) {
            try {
                Query query = entityManager.createNativeQuery(
                        "insert into users (username, password)" +
                                "values (:username, :password)");
                query.setParameter("username", user.getUsername());
                query.setParameter("password", user.getPassword());
                query.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


}
