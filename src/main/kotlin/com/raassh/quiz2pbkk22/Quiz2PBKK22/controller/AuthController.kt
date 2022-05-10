package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.S3Service
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.`interface`.IAuthService
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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
    private lateinit var userService: IAuthService

    @Autowired
    private lateinit var s3Service: S3Service

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @GetMapping("/login")
    fun loginPage() = Views.LOGIN

    @GetMapping("/register")
    fun registerPage(model: Model): String {
        model.addAttribute("registerForm", RegisterForm())

        return Views.REGISTER
    }

    @PostMapping("/register", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun processRegister(
        @ModelAttribute @Valid registerForm: RegisterForm, bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return Views.REGISTER
        }

        // for some reason, @Size in RegisterForm is not working.
        // so we need to validate password length here
        if (registerForm.password!!.length < 8) {
            bindingResult.rejectValue("password", "password.min.length", "Password need to be at least 8 characters")
            return Views.REGISTER
        }

        var url: String? = null

        if (registerForm.avatar?.isEmpty == false) {
            if (registerForm.avatar.contentType?.startsWith("image/") == true) {
                url = s3Service.save(registerForm.avatar) ?: throw java.lang.Exception("Failed to upload image")
            } else {
                bindingResult.rejectValue("avatar", "avatar.wrong.type", "Avatar must be image")
                return Views.REGISTER
            }
        }

        try {
            userService.registerNewUserAccount(registerForm, url)
            autoLogin(registerForm.email!!, registerForm.password)
        } catch (e: Exception) {
            bindingResult.reject("Exception", e.message ?: "Something went wrong")
            return Views.REGISTER
        }

        return "redirect:publishers"
    }

    fun autoLogin(username: String, password: String) {
        val authToken = UsernamePasswordAuthenticationToken(username, password)
        val auth = authenticationManager.authenticate(authToken)

        if (auth.isAuthenticated) {
            SecurityContextHolder.getContext().authentication = auth
        }
    }
}