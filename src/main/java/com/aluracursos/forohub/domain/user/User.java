package com.aluracursos.forohub.domain.user;

import com.aluracursos.forohub.domain.user.dto.CreateUserDTO;
import com.aluracursos.forohub.domain.user.dto.UpdateUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity(name = "User")
@EqualsAndHashCode(of = "id")
public class User {
    @SuppressWarnings("unused")
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String lastname;
    private String email;
    private Boolean enabled;

    private String capitalized(String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public User(CreateUserDTO createUserDTO, String hashedPassword) {
        this.username = createUserDTO.username();
        this.password = hashedPassword;
        this.role = Role.USER;
        this.name = capitalized(createUserDTO.name());
        this.lastname = capitalized(createUserDTO.lastname());
        this.email = createUserDTO.email();
        this.enabled = true;
    }

    public void UpdateUserWhitPassword(UpdateUserDTO updateUserDTO, String hashedPassword) {
        if (updateUserDTO.password() != null) {
            this.password = hashedPassword;
        }

        if (updateUserDTO.role() != null) {
            this.role = updateUserDTO.role();
        }

        if (updateUserDTO.name() != null) {
            this.name = capitalized(updateUserDTO.name());
        }

        if (updateUserDTO.lastname() != null) {
            this.lastname = capitalized(updateUserDTO.lastname());
        }

        if (updateUserDTO.email() != null) {
            this.email = updateUserDTO.email();
        }

        if (updateUserDTO.enabled() != null) {
            this.enabled = updateUserDTO.enabled();
        }
    }

    public void UpdateUser(UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.role() != null) {
            this.role = updateUserDTO.role();
        }

        if (updateUserDTO.name() != null) {
            this.name = capitalized(updateUserDTO.name());
        }

        if (updateUserDTO.lastname() != null) {
            this.lastname = capitalized(updateUserDTO.lastname());
        }

        if (updateUserDTO.email() != null) {
            this.email = updateUserDTO.email();
        }

        if (updateUserDTO.enabled() != null) {
            this.enabled = updateUserDTO.enabled();
        }
    }

    public void deleteUser() {
        this.enabled = false;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
