package com.raassh.quiz2pbkk22.Quiz2PBKK22.Controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Publisher
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.PublisherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import java.lang.Exception

@Controller
class PublisherController {
    @Autowired()
    lateinit var publisherRepository: PublisherRepository

    @GetMapping("/publishers")
    fun getAll(model: Model): String {
        try {
            val publishers = publisherRepository.findAll()

            model.addAttribute("publishers", publishers)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return "publishers"
    }

}