package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.PasswordMatches
import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.ValidEmail
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@PasswordMatches
data class RegisterForm(
    @NotNull
    @NotEmpty
    val name: String? = null,

    @NotNull
    @Size(min = 8)
    val password: String? = null,
    val password_confirmation: String? = null,

    @NotNull
    @NotEmpty
    @ValidEmail
    val email: String? = null
)