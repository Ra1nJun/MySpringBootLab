package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    @Column
    private String description;

    @Column
    private String language;

    @Column
    private Integer pageCount;

    @Column(nullable = false)
    private String publisher;

    @Column
    private String coverImageUrl;

    @Column
    private String edition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
}
