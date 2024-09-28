package goormthonUniv.floating.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String googleId;
    private String email;
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favorite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String googleId, String email, String name, Role role) {
        this.googleId = googleId;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public static User toEntity(String googleId, String email, String name, Role role) {
        return User.builder()
                .googleId(googleId)
                .email(email)
                .name(name)
                .role(role)
                .build();
    }

    public User updateNameAndEmail(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }


}
