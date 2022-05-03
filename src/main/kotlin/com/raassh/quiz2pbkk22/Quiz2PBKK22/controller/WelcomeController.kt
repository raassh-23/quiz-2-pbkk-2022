package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WelcomeController {
    @GetMapping("/")
    fun welcomePage(
        @RequestParam(name = "name", required = false, defaultValue = "world") name: String,
        model: Model
    ): String {
        model.addAttribute("name", name)

        return Views.WELCOME
    }
}