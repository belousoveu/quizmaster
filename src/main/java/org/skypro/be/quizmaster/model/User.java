package org.skypro.be.quizmaster.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "app_user")
public class User implements UserDetails {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank(message = "Поле 'Имя пользователя' не может быть пустым")
    @Size(min = 2, max = 30, message = "Поле 'Имя пользователя' должно содержать от 2 до 30 символов")
    private String username;
    @Setter
    @NotBlank(message = "Поле 'Пароль' не может быть пустым")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    public void setRoles(Set<String> roles) {
        this.roles = new HashSet<>(roles);
    }

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
