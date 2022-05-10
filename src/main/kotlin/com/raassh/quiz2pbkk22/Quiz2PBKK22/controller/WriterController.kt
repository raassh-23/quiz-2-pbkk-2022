package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.WriterRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/writers")
class WriterController {
    @Autowired
    private lateinit var writerRepository: WriterRepository

    @GetMapping
    fun getAll(
        @RequestParam(name="search", required = false, defaultValue = "") search: String,
        model: Model
    ): String {
        try {
            val writers = if (search.isEmpty()){
                writerRepository.findAll()
            } else {
                writerRepository.findByNameContainingIgnoreCase(search)
            }

            model.addAttribute("writers", writers)
        } catch (e: Exception){
            model.addAttribute("error", e.message)
        }

        return Views.WRITER_INDEX
    }

    @GetMapping("/{id}")
    fun findWriter(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            writerRepository.findById(id).ifPresentOrElse({
                val writer = it
                model.addAttribute("writer", writer)
            }) {
                model.addAttribute("error", "Writer Not Found")
            }


        } catch (e: java.lang.Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.WRITER_DETAIL
    }
}