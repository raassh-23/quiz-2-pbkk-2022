package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.ReviewForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.BookRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
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
            }) {
                model.addAttribute("error", "Book Not Found")
            }

            model.addAttribute("reviewForm", ReviewForm())
        } catch (e: java.lang.Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.BOOK_DETAIL
    }
}