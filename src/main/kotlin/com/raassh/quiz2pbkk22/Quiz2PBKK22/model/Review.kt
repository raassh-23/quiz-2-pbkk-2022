package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="reviews")
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var rating: Int = 0,
    var review: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name="book_id")
    val book: Book? = null,

    @ManyToOne
    @JoinColumn(name="user_id")
    val user: User? = null
)
