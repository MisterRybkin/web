package ru.rybkin.web.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rybkin.web.user.models.NewsUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<NewsUser, Long> {
    Optional<NewsUser> findByLogin(String login);
}
