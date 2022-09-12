package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id_organization =?")
@Where(clause = "deleted = false")
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "organizations")
@Builder
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name can not be empty.")
    private String name;

    @NotNull(message = "Image can not be empty.")
    private String image;

    private String address;

    private Integer phone;

    @Column(nullable = false)
    @NotNull(message = "Email can not be empty.")
    @Email
    private String email;

    @Column(nullable = false, name = "welcome_text", columnDefinition = "TEXT")
    @NotNull(message = "Welcome Text can not be empty.")
    private String welcomeText;

    @Column(name = "about_us_text", columnDefinition = "TEXT")
    private String aboutUsText;

    @Column(name = "url_facebook")
    private String urlFacebook;

    @Column(name = "url_linkedin")
    private String urlLinkedin;

    @Column(name = "url_instagram")
    private String urlInstagram;

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Slide> slides;

    @Column(nullable = false, updatable = false, name = "created_date")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @Column(nullable = false, updatable = false, name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

    private boolean deleted;
}
