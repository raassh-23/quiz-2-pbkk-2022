package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller.admin

import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.ReviewRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.lang.Exception

@Controller
@RequestMapping("/admin/reviews")
class AdminReviewController {
    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @GetMapping("")
    fun index(model: Model): String {
        try {
            val reviews = reviewRepository.findAll()

            model.addAttribute("reviews", reviews)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.ADMIN_REVIEWS_INDEX
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            val reviewOptional = reviewRepository.findById(id)

            if (reviewOptional.isEmpty) {
                return "redirect:/admin/reviews?error=Review deletion failed, id was not found"
            }

            reviewRepository.deleteById(id)
        } catch (e: Exception) {
            return "redirect:/admin/reviews?error=Review deletion failed"
        }

        return "redirect:/admin/reviews?success=Review deleted successfully"
    }
}