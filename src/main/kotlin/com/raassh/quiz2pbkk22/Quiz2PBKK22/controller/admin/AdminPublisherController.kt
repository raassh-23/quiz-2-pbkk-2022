package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller.admin

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.PublisherForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Publisher
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.PublisherRepository
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
@RequestMapping("/admin/publishers")
class AdminPublisherController {
    @Autowired
    private lateinit var publisherRepository: PublisherRepository

    @Autowired
    private lateinit var s3Service: S3Service

    @GetMapping("")
    fun index(model: Model): String {
        try {
            val publishers = publisherRepository.findAll()

            model.addAttribute("publishers", publishers)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.ADMIN_PUBLISHERS_INDEX
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        try {
            model.addAttribute("publisherForm", PublisherForm())
        } catch (e: Exception) {
            return "redirect:/admin/publishers?error=Show create form failed"
        }

        return Views.ADMIN_PUBLISHERS_CREATE
    }

    @PostMapping("/store", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun store(
        @ModelAttribute @Valid publisherForm: PublisherForm, bindingResult: BindingResult
    ): String {
        if (publisherForm.image?.isEmpty == true) {
            bindingResult.rejectValue("image", "image.no.data", "must have image")
        }

        if (bindingResult.hasErrors()) {
            return Views.ADMIN_PUBLISHERS_CREATE
        }

        try {
            val url = s3Service.save(publisherForm.image!!) ?: throw Exception("Failed to upload image")

            val publisher = Publisher(
                name = publisherForm.name!!,
                phone = publisherForm.phone!!,
                address = publisherForm.address!!,
                email = publisherForm.email!!,
                image_url = url
            )

            publisherRepository.save(publisher)
        } catch (e: Exception) {
            return "redirect:/admin/publishers/create?error=Publisher creation failed"
        }

        return "redirect:/admin/publishers?success=Publisher added successfully"
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            val publisherOptional = publisherRepository.findById(id)

            if (publisherOptional.isEmpty) {
                return "redirect:/admin/publishers?error=Publisher deletion failed, id was not found"
            }

            publisherRepository.deleteById(id)
        } catch (e: Exception) {
            return "redirect:/admin/publishers?error=Publisher deletion failed"
        }

        return "redirect:/admin/publishers?success=Publisher deleted successfully"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        try {
            val publisherOptional = publisherRepository.findById(id)

            if (publisherOptional.isEmpty) {
                return "redirect:/admin/publishers?error=Show edit form failed, id was not found"
            }

            val publisher = publisherOptional.get()

            model.addAttribute("publisherForm",
                PublisherForm(
                    name = publisher.name,
                    phone = publisher.phone,
                    address = publisher.address,
                    email = publisher.email,
                )
            )
            addPublisherAttribute(publisher, model)
        } catch (e: Exception) {
            return "redirect:/admin/publishers?error=Show edit form failed"
        }

        return Views.ADMIN_PUBLISHERS_EDIT
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long, @ModelAttribute @Valid publisherForm: PublisherForm, bindingResult: BindingResult, model: Model
    ): String {
        try {
            val publisherOptional = publisherRepository.findById(id)

            if (publisherOptional.isEmpty) {
                return "redirect:/admin/publishers?error=Show edit form failed, id was not found"
            }

            val publisher = publisherOptional.get()

            if (bindingResult.hasErrors()) {
                addPublisherAttribute(publisher, model)
                return Views.ADMIN_PUBLISHERS_EDIT
            }

            val url = if (publisherForm.image?.isEmpty == false) {
                s3Service.save(publisherForm.image) ?: throw Exception("Failed to upload image")
            } else {
                publisher.image_url
            }

            publisher.apply {
                name = publisherForm.name!!
                phone = publisherForm.phone!!
                address = publisherForm.address!!
                email = publisherForm.email!!
                image_url = url
                updated_at = LocalDateTime.now()
            }

            publisherRepository.save(publisher)
        } catch (e: Exception) {
            return "redirect:/admin/publishers/edit/$id?error=Publisher edit failed"
        }

        return "redirect:/admin/publishers?success=Publisher updated successfully"
    }

    private fun addPublisherAttribute(publisher: Publisher, model: Model) {
        model.addAttribute("publisherId", publisher.id)
        model.addAttribute("publisherName", publisher.name)
        model.addAttribute("publisherImage", publisher.image_url)
    }
}