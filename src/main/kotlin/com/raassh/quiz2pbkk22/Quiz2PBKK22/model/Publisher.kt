package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

// ref: https://www.bezkoder.com/spring-boot-postgresql-example/

@Entity
@Table(name = "publishers")
data class Publisher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val email: String = "",
    val phone: String = "",
    val image_url: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "publisher")
    val books: List<Book>? = null
)