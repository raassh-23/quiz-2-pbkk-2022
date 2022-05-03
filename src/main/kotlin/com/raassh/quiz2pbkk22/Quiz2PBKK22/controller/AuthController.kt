package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.UserService
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.`interface`.IUserService
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class AuthController {
    @Autowired
    private lateinit var userService: IUserService

    @GetMapping("/login")
    fun loginPage() = Views.LOGIN

    @GetMapping("/register")
    fun registerPage(model: Model): String {
        model.addAttribute("registerForm", RegisterForm())

        return Views.REGISTER
    }

    @PostMapping("/register")
    fun processRegister(
        @ModelAttribute @Valid registerForm: RegisterForm, bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return Views.REGISTER
        }

        try {
            userService.registerNewUserAccount(registerForm)
        } catch (e: Exception) {
            bindingResult.reject("Exception", e.message ?: "Something went wrong")
            return Views.REGISTER
        }

        return "redirect:publishers"
    }
}