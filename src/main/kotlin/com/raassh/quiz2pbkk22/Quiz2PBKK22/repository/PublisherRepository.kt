package com.raassh.quiz2pbkk22.Quiz2PBKK22.repository

import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Publisher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface PublisherRepository : JpaRepository<Publisher, Long> {
    fun findByNameContainingIgnoreCase(@Param("name") name: String): List<Publisher>
}