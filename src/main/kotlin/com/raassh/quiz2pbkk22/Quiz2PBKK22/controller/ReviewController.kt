package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.ReviewForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Review
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.BookRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.ReviewRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.time.LocalDateTime
import javax.validation.Valid

@Controller
@RequestMapping("/review")
class ReviewController {
    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    @PostMapping("/store")
    fun store(
        @ModelAttribute @Valid reviewForm: ReviewForm, bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return Views.BOOK_DETAIL
        }

        val user = userRepository.findById(reviewForm.user_id!!).get()
        val book = bookRepository.findById(reviewForm.book_id!!).get()

        try {
            val review = Review(
                rating = reviewForm.rating as Int,
                review = reviewForm.review as String,
                user = user,
                book = book
            )

            reviewRepository.save(review)
        } catch (e: Exception) {
            return "redirect:/book/detail/${book.id}?error=Adding review failed"
        }

        return "redirect:/book/detail/${book.id}?success=Adding review success"
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String {
        val reviewOptional = reviewRepository.findById(id)

        try {
            if (reviewOptional.isEmpty) {
                return "redirect:/book/index/error=Deleting review failed, ID not found"
            }

            reviewRepository.deleteById(id)
        } catch (e: Exception) {
            return "redirect:/book/detail/${reviewOptional.get().book!!.id}?error=Deleting review failed"
        }

        return "redirect:/book/detail/${reviewOptional.get().book!!.id}?success=Deleting review success"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        try {
            val reviewOptional = reviewRepository.findById(id)

            if (reviewOptional.isEmpty) {
                return "redirect:/admin/categories?error=Show edit form failed, id was not found"
            }

            val review = reviewOptional.get()

            model.addAttribute("reviewForm", ReviewForm(
                rating = review.rating,
                review = review.review,
                user_id = review.user?.id,
                book_id = review.book?.id
            ))
            model.addAttribute("reviewId", review.id)
        } catch (e: Exception) {
            return "redirect:/admin/categories?error=Show edit form failed"
        }

        return Views.ADMIN_CATEGORIES_EDIT
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long, @ModelAttribute @Valid reviewForm: ReviewForm, bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return Views.BOOK_DETAIL
        }

        try {
            val reviewOptional = reviewRepository.findById(id)

            if (reviewOptional.isEmpty) {
                return "redirect:/book/detail/${reviewForm.book_id}?error=Updating review failed, ID not found"
            }

            val review = reviewOptional.get().apply {
                rating = reviewForm.rating as Int
                review = reviewForm.review as String
                updated_at = LocalDateTime.now()
            }

            reviewRepository.save(review)
        } catch (e: Exception) {
            return "redirect:/book/detail/${reviewForm.book_id}?error=Adding review failed"
        }

        return "redirect:/book/detail/${reviewForm.book_id}?error=Adding review failed"
    }
}