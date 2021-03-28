package ru.rybkin.web.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rybkin.web.user.models.NewsUser;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String userName;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUser(String userName, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(NewsUser newsUser) {
        return new org.springframework.security.core.userdetails.User(
                newsUser.getLogin(),
                newsUser.getPassword(),
                newsUser.getUserStatus().getName().equals("ACTIVE"),
                newsUser.getUserStatus().getName().equals("ACTIVE"),
                newsUser.getUserStatus().getName().equals("ACTIVE"),
                newsUser.getUserStatus().getName().equals("ACTIVE"),
                Collections.singletonList(new SimpleGrantedAuthority(newsUser.getRole().getName()))
        );
    }
}
