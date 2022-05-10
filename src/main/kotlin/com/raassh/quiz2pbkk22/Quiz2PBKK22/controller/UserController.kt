package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.lang.Exception

@Controller
@RequestMapping("/users")
class UserController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("")
    fun getAll(
        @RequestParam(name = "search", required = false, defaultValue = "") search: String,
        model: Model
    ): String {
        try {
            val users = if (search.isEmpty()) {
                userRepository.findAll()
            } else {
                userRepository.findByNameContainingIgnoreCase(search)
            }

            model.addAttribute("users", users)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.USER_INDEX
    }

    @GetMapping("/{id}")
    fun findUser(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            userRepository.findById(id).ifPresentOrElse({
                val user = it
                model.addAttribute("user", user)
            }) {
                model.addAttribute("error", "User Not Found")
            }


        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.USER_DETAIL
    }
}