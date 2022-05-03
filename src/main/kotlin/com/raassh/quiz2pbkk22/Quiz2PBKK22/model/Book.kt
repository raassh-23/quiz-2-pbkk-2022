package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String = "",
    val synopsis: String = "",
    val edition: Int = 0,
    val publish_year: Int = 0,
    val cover_image: String = "",
    val isbn: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "publisher_id")
    val publisher: Publisher? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "category_id")
    val category: Category? = null,

    @OneToMany(mappedBy = "book")
    val reviews: List<Review>? = null
)