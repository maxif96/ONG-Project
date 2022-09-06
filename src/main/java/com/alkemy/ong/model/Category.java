package com.alkemy.ong.model;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.validation.constraints.NotNull;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name can not be null")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Image can not be null")
    private String image;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createDate;
    
    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column
    private boolean deleted;

    public Category(String name, String description, String image, LocalDateTime createDate) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.createDate = createDate;
    }
}
