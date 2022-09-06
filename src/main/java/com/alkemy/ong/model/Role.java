package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message =  "Name can not be null")
    private String name;

    private String description;

    @Column(name = "created_date", updatable=false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updateAt;
}
