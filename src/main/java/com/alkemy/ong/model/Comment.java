package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment can not be empty")
    @Column(nullable = false)
    private String body;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(name = "comments_users",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @NotNull(message = "User can not be null")
    private Users user;

    @ManyToOne
    @JoinTable(name = "comments_news",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "news_id")})
    @NotNull(message = "News can not be null")
    private News news;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updateAt;

}
