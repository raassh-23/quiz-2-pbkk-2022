package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.BookRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WelcomeController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    @GetMapping("/")
    fun welcomePage(model: Model): String {
        try {
            val books = bookRepository.findAll()
            val mostReviewedBooks = books.sortedByDescending { it.reviews?.size ?: 0 }.take(6)
            val highestRatingBooks = books.sortedByDescending { it.rating() ?: 0f }.take(6)

            val topUsers = userRepository.findAll().sortedByDescending { it.reviews?.size ?: 0 }.take(4)

            model.addAttribute("mostReviewedBooks", mostReviewedBooks)
            model.addAttribute("highestRatingBooks", highestRatingBooks)
            model.addAttribute("topUsers", topUsers)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.WELCOME
    }
}