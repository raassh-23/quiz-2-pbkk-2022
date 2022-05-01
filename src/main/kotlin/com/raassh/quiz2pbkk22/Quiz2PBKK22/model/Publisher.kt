package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

// ref: https://www.bezkoder.com/spring-boot-postgresql-example/

@Entity
@Table(name = "publishers")
data class Publisher (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,
    @Column val name: String = "",
    @Column val address: String = "",
    @Column val email: String = "",
    @Column val phone: String = "",
    @Column val image_url: String = "",
    @Column val created_at: LocalDateTime = LocalDateTime.now(),
    @Column val updated_at: LocalDateTime = LocalDateTime.now()
)