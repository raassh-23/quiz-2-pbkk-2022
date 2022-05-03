package com.raassh.quiz2pbkk22.Quiz2PBKK22.repository

import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}