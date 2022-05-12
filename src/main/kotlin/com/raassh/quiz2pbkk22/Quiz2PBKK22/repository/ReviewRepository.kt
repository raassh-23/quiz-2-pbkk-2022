package com.raassh.quiz2pbkk22.Quiz2PBKK22.repository

import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Book
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Review
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface ReviewRepository : JpaRepository<Review, Long>{
    fun findByBookAndUser(@Param("book") book: Book, @Param("user") user: User): Review
}