package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name="Books")
@DynamicUpdate
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column
    private LocalDate publishDate;

    @Column
    private int price;
}
