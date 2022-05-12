package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.ReviewForm
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
    val created_at: LocalDateTime = LocalDateTime.now(),
    var updated_at: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    val book: Book? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    val user: User? = null
)
