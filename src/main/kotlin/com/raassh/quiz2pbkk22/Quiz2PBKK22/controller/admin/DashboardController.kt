package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller.admin

import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.*
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.lang.Exception

@Controller
class DashboardController {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var publisherRepository: PublisherRepository

    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @Autowired
    private lateinit var writerRepository: WriterRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/admin")
    fun welcomePage(model: Model): String {
        try {
            model.addAttribute("books_count", bookRepository.count())
            model.addAttribute("writers_count", writerRepository.count())
            model.addAttribute("publishers_count", publisherRepository.count())
            model.addAttribute("categories_count", categoryRepository.count())
            model.addAttribute("reviews_count", reviewRepository.count())
            model.addAttribute("users_count", userRepository.count())
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.ADMIN_DASHBOARD
    }
}