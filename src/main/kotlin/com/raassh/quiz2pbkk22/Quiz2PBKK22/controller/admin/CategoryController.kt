package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller.admin

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.CategoryForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Category
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.CategoryRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.time.LocalDateTime
import javax.validation.Valid

@Controller
@RequestMapping("/admin/categories")
class CategoryController {
    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @GetMapping("")
    fun index(model: Model): String {
        try {
            val categories = categoryRepository.findAll()

            model.addAttribute("categories", categories)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.ADMIN_CATEGORIES_INDEX
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        model.addAttribute("categoryForm", CategoryForm())

        return Views.ADMIN_CATEGORIES_CREATE
    }

    @PostMapping("/store")
    fun store(
        @ModelAttribute @Valid categoryForm: CategoryForm, bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return Views.ADMIN_CATEGORIES_CREATE
        }

        try {
            val category = Category(
                name = categoryForm.name as String
            )

            categoryRepository.save(category)
        } catch (e: Exception) {
            return "redirect:/admin/categories/create?error=Category creation failed"
        }

        return "redirect:/admin/categories?success=Category added successfully"
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            val categoryOptional = categoryRepository.findById(id)

            if (categoryOptional.isEmpty) {
                return "redirect:/admin/categories?error=Category deletion failed, id was not found"
            }

            categoryRepository.deleteById(id)
        } catch (e: Exception) {
            return "redirect:/admin/categories?error=Category deletion failed"
        }

        return "redirect:/admin/categories?success=Category deleted successfully"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        try {
            val categoryOptional = categoryRepository.findById(id)

            if (categoryOptional.isEmpty) {
                return "redirect:/admin/categories?error=Show edit form failed, id was not found"
            }

            val category = categoryOptional.get()

            model.addAttribute("categoryForm", CategoryForm(
                name = category.name
            ))
            model.addAttribute("categoryId", category.id)
        } catch (e: Exception) {
            return "redirect:/admin/categories?error=Show edit form failed"
        }

        return Views.ADMIN_CATEGORIES_EDIT
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long, @ModelAttribute @Valid categoryForm: CategoryForm, bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return Views.ADMIN_CATEGORIES_EDIT
        }

        try {
            val categoryOptional = categoryRepository.findById(id)

            if (categoryOptional.isEmpty) {
                return "redirect:/admin/categories/edit/$id?error=Show edit form failed, id was not found"
            }

            val category = categoryOptional.get().apply {
                name = categoryForm.name as String
                updated_at = LocalDateTime.now()
            }

            categoryRepository.save(category)
        } catch (e: Exception) {
            return "redirect:/admin/categories/edit/$id?error=Category edit failed"
        }

        return "redirect:/admin/categories?success=Category updated successfully"
    }
}