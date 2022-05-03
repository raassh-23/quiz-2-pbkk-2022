package com.raassh.quiz2pbkk22.Quiz2PBKK22.repository

import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface BookRepository : JpaRepository<Book, Long> {
    fun findByNameContainingIgnoreCase(@Param("name") name: String): List<Book>
}