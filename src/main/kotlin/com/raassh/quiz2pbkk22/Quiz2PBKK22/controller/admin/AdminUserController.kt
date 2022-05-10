package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller.admin

import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.User
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.UserRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.lang.Exception
import java.time.LocalDateTime

@Controller
@RequestMapping("/admin/users")
class AdminUserController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("")
    fun index(model: Model): String {
        try {
            val users = userRepository.findAll()

            model.addAttribute("users", users)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.ADMIN_USERS_INDEX
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            val userOptional = userRepository.findById(id)

            if (userOptional.isEmpty) {
                return "redirect:/admin/users?error=User deletion failed, id was not found"
            }

            if (isSelf(userOptional.get())) {
                return "redirect:/admin/users?error=User deletion failed, you can't delete yourself"
            }

            userRepository.deleteById(id)
        } catch (e: Exception) {
            return "redirect:/admin/users?error=User deletion failed"
        }

        return "redirect:/admin/users?success=User deleted successfully"
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            val userOptional = userRepository.findById(id)

            if (userOptional.isEmpty) {
                return "redirect:/admin/users?error=Role change failed, id was not found"
            }

            val user = userOptional.get()

            if (isSelf(user)) {
                return "redirect:/admin/users?error=Role change failed, you can't demote yourself"
            }

            user.apply {
                role = if (role == 0) {
                    1
                } else {
                    0
                }
                updated_at = LocalDateTime.now()
            }

            userRepository.save(user)
        } catch (e: Exception) {
            return "redirect:/admin/users?error=Role change failed"
        }

        return "redirect:/admin/users?success=Role changed successfully"
    }

    fun isSelf(user: User): Boolean {
        val userDetails =
            SecurityContextHolder.getContext().authentication.principal as org.springframework.security.core.userdetails.User

        return user.email == userDetails.username
    }
}