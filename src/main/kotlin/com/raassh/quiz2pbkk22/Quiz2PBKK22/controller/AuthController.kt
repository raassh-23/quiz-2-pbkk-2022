package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.S3Service
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.`interface`.IUserService
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import javax.validation.Valid


@Controller
class AuthController {
    @Autowired
    private lateinit var userService: IUserService

    @Autowired
    private lateinit var s3Service: S3Service

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
            bindingResult.rejectValue("password", "password.min.length", "Password need to be atleast 8 characters")
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
        } catch (e: Exception) {
            bindingResult.reject("Exception", e.message ?: "Something went wrong")
            return Views.REGISTER
        }

        return "redirect:publishers"
    }
}