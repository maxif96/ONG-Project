package com.alkemy.ong.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@SQLDelete(sql = "UPDATE contacts SET deleted = true WHERE id_contact =?")
@Where(clause = "deleted = false")
@Getter
@Setter
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name can not be empty.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Phone can not be empty.")
    private Integer phone;

    @Column(nullable = false)
    @NotBlank(message = "Email can not be empty.")
    @Email
    private String email;

    private String message;

    private boolean deleted;
}
