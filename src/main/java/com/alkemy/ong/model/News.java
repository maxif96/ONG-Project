package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE news SET deleted = true WHERE id =?")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name can not be empty.")
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Content can not be empty.")
    private String content;

    private String image;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Date updateDate;

    @Column
    private boolean deleted;

    public News(String name, String content, String image, Category category, Date date) {
        this.name = name;
        this.content = content;
        this.image = image;
        this.category = category;
        this.createDate = date;

    }
}