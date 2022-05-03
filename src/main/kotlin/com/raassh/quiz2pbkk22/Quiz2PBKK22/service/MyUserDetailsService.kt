package com.raassh.quiz2pbkk22.Quiz2PBKK22.service

import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MyUserDetailsService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username as String)
            ?: throw UsernameNotFoundException("No user found for $username")

        val enabled = true
        val accountNonExpired = true
        val credentialsNonExpired = true
        val accountNonLocked = true
        val authorities = listOf(
            SimpleGrantedAuthority(
                if (user.role == 1) {
                    "ADMIN"
                } else {
                    "USER"
                }
            )
        )

        return User(user.email, user.password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
    }
}