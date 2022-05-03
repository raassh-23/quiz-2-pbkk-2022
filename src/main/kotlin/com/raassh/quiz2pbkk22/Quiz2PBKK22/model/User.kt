package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val role: Int = 0,
    val avatar_url: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime = LocalDateTime.now()
)