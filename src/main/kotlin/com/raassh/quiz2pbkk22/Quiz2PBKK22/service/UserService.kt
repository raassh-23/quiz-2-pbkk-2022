package com.raassh.quiz2pbkk22.Quiz2PBKK22.service

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.User
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.`interface`.IUserService
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.md5
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService : IUserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun registerNewUserAccount(registerForm: RegisterForm, avatar: String?) {
        if (userRepository.findByEmail(registerForm.email as String) != null) {
            throw Exception("Email already exists")
        }

        val avatar_url = avatar ?: "https://www.gravatar.com/avatar/${md5(registerForm.email.lowercase().trim())}?d=identicon&r=g&s=128"

        val user = User(
            name = registerForm.name as String,
            email = registerForm.email,
            password = passwordEncoder.encode(registerForm.password),
            avatar_url = avatar_url,
            role = 0
        )

        userRepository.save(user)
    }
}