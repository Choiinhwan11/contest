package org.example.contest.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.contest.domain.interested.entity.InterestedInformation;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", unique = true, nullable = false)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", unique = true, nullable = false)
    private Long phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType providerType;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterestedInformation> interests;

    @Builder
    public User(Long id ,String userId, String name, String email, Long phone, ProviderType providerType) {
        this.id =id ;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.providerType = providerType;
    }
}
