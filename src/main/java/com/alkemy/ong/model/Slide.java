package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "slides")
@SQLDelete(sql = "UPDATE slides SET deleted = true WHERE id =?")
@Where(clause = "deleted = false")
public class Slide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Integer position;
    @ManyToOne
    @JoinTable(name = "slide_organization",
            joinColumns = {@JoinColumn(name = "slide_id")},
            inverseJoinColumns = {@JoinColumn(name = "organization_id")})
    private Organization organization;
    private boolean deleted;

}
