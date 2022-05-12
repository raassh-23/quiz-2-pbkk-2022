package com.raassh.quiz2pbkk22.Quiz2PBKK22.utils

import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class LoggedInUserControllerAdvice  {
    @Autowired
    lateinit var userRepository: UserRepository

    @ModelAttribute
    fun handleRequest(model: Model) {
        val principal = SecurityContextHolder.getContext().authentication.principal ?: return

        if (principal !is User) {
            return
        }

        val user = userRepository.findByEmail(principal.username)

        model.addAttribute("LoggedInUser", user)
    }
}