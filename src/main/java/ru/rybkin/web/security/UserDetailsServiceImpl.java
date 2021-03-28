package ru.rybkin.web.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rybkin.web.user.models.Role;
import ru.rybkin.web.user.models.User;
import ru.rybkin.web.user.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));
        Role role = user.getRole();
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(role.getName())
        );
        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(), authorities);
    }
}