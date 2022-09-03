package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @Column(name = "id_organization")
    private Long idOrganization;

    @Column(nullable = false, name = "name")
    @NotNull(message = "Name can not be empty.")
    private String name;

    @Column(name = "image")
    @NotNull(message = "Image can not be empty.")
    private String image;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Integer phone;

    @Column(nullable = false, name = "email")
    @NotNull(message = "Email can not be empty.")
    @Email
    private String email;

    @Column(nullable = false, name = "welcome_text", columnDefinition = "TEXT")
    @NotNull(message = "WelcomeText can not be empty.")
    private String welcomeText;

    @Column(name = "about_us_text", columnDefinition = "TEXT")
    private String aboutUsText;

    @Column(name = "url_facebook")
    private String urlFacebook;

    @Column(name = "url_linkedin")
    private String urlLinkedin;

    @Column(name = "url_instagram")
    private String urlInstagram;

    @Column(nullable = false, updatable = false, name = "creation_timestamp")
    @CreationTimestamp
    private Date creationTimestamp;

    @Column(nullable = false, updatable = false, name = "update_timestamp")
    @UpdateTimestamp
    private Date updateTimestamp;

    @Column
    private boolean deleted;
}
