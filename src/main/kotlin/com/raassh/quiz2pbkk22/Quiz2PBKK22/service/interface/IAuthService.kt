package com.raassh.quiz2pbkk22.Quiz2PBKK22.service.`interface`

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm

interface IAuthService {
    fun registerNewUserAccount(registerForm: RegisterForm, avatar: String? = null)
}