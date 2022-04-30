package com.raassh.quiz2pbkk22.Quiz2PBKK22.welcome

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class Welcome {
    @GetMapping("/")
    fun welcome(
        @RequestParam(name = "name", required = false, defaultValue = "world") name: String,
        model: Model
    ): String {
        model.addAttribute("name", name)

        return "welcome"
    }
}