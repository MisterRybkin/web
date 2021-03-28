package ru.rybkin.web.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rybkin.web.user.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
