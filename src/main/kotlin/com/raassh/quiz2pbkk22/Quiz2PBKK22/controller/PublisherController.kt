package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.PublisherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.lang.Exception

@Controller
class PublisherController {
    @Autowired()
    lateinit var publisherRepository: PublisherRepository

    @GetMapping("/publishers")
    fun getAll(
        @RequestParam(name = "search", required = false, defaultValue = "") search: String,
        model: Model
    ): String {
        try {
            val publishers = if (search.isEmpty()) {
                publisherRepository.findAll()
            } else {
                publisherRepository.findByNameContainingIgnoreCase(search)
            }

            model.addAttribute("publishers", publishers)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return "publishers/index"
    }

    @GetMapping("/publishers/{id}")
    fun findPublisher(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            publisherRepository.findById(id).ifPresentOrElse({
                val publisher = it
                model.addAttribute("publisher", publisher)
            }) {
                model.addAttribute("error", "Publisher Not Found")
            }


        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return "publishers/detail"
    }
}