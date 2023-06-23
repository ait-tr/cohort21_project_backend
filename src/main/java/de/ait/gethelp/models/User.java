package de.ait.gethelp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    public enum Role {
        USER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "username")
    private String username;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    // TODO Image/String avatar;

    // TODO Address = Country + City;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "is_helper")
    private Boolean isHelper;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @OneToMany(mappedBy = "user")
    @Column(name = "role")
    private List<Card> cards;
}
