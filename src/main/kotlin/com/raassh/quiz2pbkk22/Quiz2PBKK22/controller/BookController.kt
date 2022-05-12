package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.ReviewForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Review
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.BookRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.ReviewRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/books")
class BookController {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @GetMapping
    fun getAll(
        @RequestParam(name="search", required = false, defaultValue = "") search: String,
        model: Model
    ): String {
        try {
            val books = if (search.isEmpty()){
                bookRepository.findAll()
            } else {
                bookRepository.findByTitleContainingIgnoreCase(search)
            }

            model.addAttribute("books", books)
        } catch (e: Exception){
            model.addAttribute("error", e.message)
        }

        return Views.BOOK_INDEX
    }

    @GetMapping("/{id}")
    fun findBook(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            bookRepository.findById(id).ifPresentOrElse({
                val book = it
                model.addAttribute("book", book)

                val principal = SecurityContextHolder.getContext().authentication.principal

                val user = if (principal is User) userRepository.findByEmail((principal as User).username) ?: null
                else null

                var reviewForm = ReviewForm()

                if (user != null && book.reviews != null) {
                    var reviewId = 0L

                    for (bookReview in book.reviews) {
                        if (bookReview.user?.id == user.id) reviewId = bookReview.id
                    }

                    reviewRepository.findById(reviewId).ifPresentOrElse({
                        val userReview = it

                        reviewForm = ReviewForm(
                            review = userReview.review,
                            rating = userReview.rating,
                            book_id = id,
                            user_id = user.id,
                        )
                    }) {
                        reviewForm = ReviewForm(
                            review = "",
                            rating = 0,
                            book_id = id,
                            user_id = user.id,
                        )
                    }
                }

                model.addAttribute("reviewForm", reviewForm)
            }) {
                model.addAttribute("error", "Book Not Found")
            }
        } catch (e: java.lang.Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.BOOK_DETAIL
    }
}