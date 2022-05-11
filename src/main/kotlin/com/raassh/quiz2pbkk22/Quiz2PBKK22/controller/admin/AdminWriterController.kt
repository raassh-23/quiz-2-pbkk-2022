package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller.admin

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.WriterForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Writer
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.BookRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.WriterRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.S3Service
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
@RequestMapping("/admin/writers")
class AdminWriterController {
    @Autowired
    private lateinit var writerRepository: WriterRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var s3Service: S3Service

    @GetMapping("")
    fun index(model: Model): String {
        try {
            val writers = writerRepository.findAll()

            model.addAttribute("writers", writers)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.ADMIN_WRITERS_INDEX
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        try {
            model.addAttribute("writerForm", WriterForm())
        } catch (e: Exception) {
            return "redirect:/admin/writers?error=Show create form failed"
        }

        return Views.ADMIN_WRITERS_CREATE
    }

    @PostMapping("/store", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun store(
        @ModelAttribute @Valid writerForm: WriterForm, bindingResult: BindingResult
    ): String {
        if (writerForm.image?.isEmpty == true) {
            bindingResult.rejectValue("image", "image.no.data", "must have image")
        }

        if (bindingResult.hasErrors()) {
            return Views.ADMIN_WRITERS_CREATE
        }

        try {
            val url = s3Service.save(writerForm.image!!) ?: throw Exception("Failed to upload image")

            val writer = Writer(
                name = writerForm.name!!,
                phone = writerForm.phone!!,
                address = writerForm.address!!,
                email = writerForm.email!!,
                image_url = url
            )

            writerRepository.save(writer)
        } catch (e: Exception) {
            return "redirect:/admin/writers/create?error=Writer creation failed"
        }

        return "redirect:/admin/writers?success=Writer added successfully"
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            val writerOptional = writerRepository.findById(id)

            if (writerOptional.isEmpty) {
                return "redirect:/admin/writers?error=Writer deletion failed, id was not found"
            }

            val bookIds = writerOptional.get().books?.map { it.id }

            writerRepository.deleteById(id)

            if (!bookIds.isNullOrEmpty()) {
                bookRepository.findAllById(bookIds).forEach {
                    if (it.writers.isEmpty()) {
                        bookRepository.delete(it)
                    }
                }
            }

        } catch (e: Exception) {
            return "redirect:/admin/writers?error=Writer deletion failed"
        }

        return "redirect:/admin/writers?success=Writer deleted successfully"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        try {
            val writerOptional = writerRepository.findById(id)

            if (writerOptional.isEmpty) {
                return "redirect:/admin/writers?error=Show edit form failed, id was not found"
            }

            val writer = writerOptional.get()

            model.addAttribute(
                "writerForm",
                WriterForm(
                    name = writer.name,
                    phone = writer.phone,
                    address = writer.address,
                    email = writer.email,
                )
            )
            addWriterAttribute(writer, model)
        } catch (e: Exception) {
            return "redirect:/admin/writers?error=Show edit form failed"
        }

        return Views.ADMIN_WRITERS_EDIT
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long,
        @ModelAttribute @Valid writerForm: WriterForm,
        bindingResult: BindingResult,
        model: Model
    ): String {
        try {
            val writerOptional = writerRepository.findById(id)

            if (writerOptional.isEmpty) {
                return "redirect:/admin/writers?error=Show edit form failed, id was not found"
            }

            val writer = writerOptional.get()

            if (bindingResult.hasErrors()) {
                addWriterAttribute(writer, model)
                return Views.ADMIN_WRITERS_EDIT
            }

            val url = if (writerForm.image?.isEmpty == false) {
                s3Service.save(writerForm.image) ?: throw Exception("Failed to upload image")
            } else {
                writer.image_url
            }

            writer.apply {
                name = writerForm.name!!
                phone = writerForm.phone!!
                address = writerForm.address!!
                email = writerForm.email!!
                image_url = url
                updated_at = LocalDateTime.now()
            }

            writerRepository.save(writer)
        } catch (e: Exception) {
            return "redirect:/admin/writers/edit/$id?error=Writer edit failed"
        }

        return "redirect:/admin/writers?success=Writer updated successfully"
    }

    private fun addWriterAttribute(writer: Writer, model: Model) {
        model.addAttribute("writerId", writer.id)
        model.addAttribute("writerName", writer.name)
        model.addAttribute("writerImage", writer.image_url)
    }
}