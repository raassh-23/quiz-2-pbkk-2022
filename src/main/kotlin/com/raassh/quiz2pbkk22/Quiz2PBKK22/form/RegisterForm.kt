package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.IsImage
import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.PasswordMatches
import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.ValidEmail
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@PasswordMatches
data class RegisterForm(
    @field:NotNull
    @field:NotEmpty
    val name: String? = null,

    @field:NotNull
    @field:Size(min = 8, message = "must have at least 8 characters")
    val password: String? = null,
    val password_confirmation: String? = null,

    @field:NotNull
    @field:NotEmpty
    @ValidEmail
    val email: String? = null,

    @field:NotNull
    @IsImage
    val avatar: MultipartFile? = null
)