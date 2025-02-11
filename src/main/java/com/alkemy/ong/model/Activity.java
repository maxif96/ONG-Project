package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "{error.name.notnull}")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "{error.content.notnull}")
    private String content;

    @Column(nullable = false)
    @NotNull(message = "{error.image.notnull}")
    private String image;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(name = "deleted")
    private boolean deleted;
}
