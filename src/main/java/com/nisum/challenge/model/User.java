package com.nisum.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Phone> phones = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;
    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;
    @CurrentTimestamp(source = SourceType.DB)
    private Instant lastLogin;

    @Column(name = "token")
    private String token;

}
