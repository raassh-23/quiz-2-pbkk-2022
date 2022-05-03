package com.raassh.quiz2pbkk22.Quiz2PBKK22.service.`interface`

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm

interface IUserService {
    fun registerNewUserAccount(registerForm: RegisterForm)
}