package org.surro.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.surro.userservice.model.Role;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
