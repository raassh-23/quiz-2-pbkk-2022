package com.raassh.quiz2pbkk22.Quiz2PBKK22.controller.admin

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.BookForm
import com.raassh.quiz2pbkk22.Quiz2PBKK22.model.Book
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.BookRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.CategoryRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.PublisherRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.repository.WriterRepository
import com.raassh.quiz2pbkk22.Quiz2PBKK22.service.S3Service
import com.raassh.quiz2pbkk22.Quiz2PBKK22.utils.Views
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@Controller
@RequestMapping("/admin/books")
class AdminBookController {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var publisherRepository: PublisherRepository

    @Autowired
    private lateinit var writerRepository: WriterRepository

    @Autowired
    private lateinit var s3Service: S3Service

    @GetMapping("")
    fun index(model: Model): String {
        try {
            val books = bookRepository.findAll()

            model.addAttribute("books", books)
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
        }

        return Views.ADMIN_BOOKS_INDEX
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        try {
            addPubCatAndWriter(model)
            model.addAttribute("bookForm", BookForm())
        } catch (e: Exception) {
            return "redirect:/admin/books?error=Show create form failed"
        }

        return Views.ADMIN_BOOKS_CREATE
    }

    @PostMapping("/store", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun store(
        @ModelAttribute @Valid bookForm: BookForm, bindingResult: BindingResult, model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            addPubCatAndWriter(model)
            return Views.ADMIN_BOOKS_CREATE
        }

        if (bookForm.cover_image?.isEmpty == true || bookForm.cover_image?.contentType?.startsWith("image/") == false) {
            bindingResult.rejectValue("cover_image", "cover_image.wrong.type", "Cover must be image")
            addPubCatAndWriter(model)
            return Views.ADMIN_BOOKS_CREATE
        }

        try {
            val category = categoryRepository.findById(bookForm.category_id!!).get()
            val publisher = publisherRepository.findById(bookForm.publisher_id!!).get()
            val writers = writerRepository.findAllById(bookForm.writer_ids!!)

            val url = s3Service.save(bookForm.cover_image!!) ?: throw java.lang.Exception("Failed to upload image")

            val book = Book(
                title = bookForm.title!!,
                synopsis = bookForm.synopsis!!,
                edition = bookForm.edition!!,
                publish_year = bookForm.publish_year!!,
                isbn = bookForm.isbn!!,
                cover_image = url,
                category = category,
                publisher = publisher,
                writers = writers
            )

            bookRepository.save(book)
        } catch (e: Exception) {
            return "redirect:/admin/books/create?error=Book creation failed"
        }

        return "redirect:/admin/books?success=Book added successfully"
    }

    private fun addPubCatAndWriter(model: Model) {
        val categories = categoryRepository.findAll()
        val publishers = publisherRepository.findAll()
        val writers = writerRepository.findAll()

        model.addAttribute("categories", categories)
        model.addAttribute("publishers", publishers)
        model.addAttribute("writers", writers)
    }

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model
    ): String {
        try {
            val bookOptional = bookRepository.findById(id)

            if (bookOptional.isEmpty) {
                return "redirect:/admin/books?error=Book deletion failed, id was not found"
            }

            bookRepository.deleteById(id)
        } catch (e: Exception) {
            return "redirect:/admin/books?error=Book deletion failed"
        }

        return "redirect:/admin/books?success=Book deleted successfully"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        try {
            val bookOptional = bookRepository.findById(id)

            if (bookOptional.isEmpty) {
                return "redirect:/admin/books?error=Show edit form failed, id was not found"
            }

            val book = bookOptional.get()

            model.addAttribute("bookForm",
                BookForm(
                    title = book.title,
                    synopsis = book.synopsis,
                    edition = book.edition,
                    publish_year = book.publish_year,
                    isbn = book.isbn,
                    publisher_id = book.publisher?.id,
                    category_id = book.category?.id,
                    writer_ids = book.writers.map { it.id }
                )
            )
            addBookAttribute(book, model)
            addPubCatAndWriter(model)
        } catch (e: Exception) {
            return "redirect:/admin/books?error=Show edit form failed"
        }

        return Views.ADMIN_BOOKS_EDIT
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: Long, @ModelAttribute @Valid bookForm: BookForm, bindingResult: BindingResult, model: Model
    ): String {
        try {
            val bookOptional = bookRepository.findById(id)

            if (bookOptional.isEmpty) {
                return "redirect:/admin/books?error=Show edit form failed, id was not found"
            }

            val book = bookOptional.get()

            if (bindingResult.hasErrors()) {
                addPubCatAndWriter(model)
                addBookAttribute(book, model)
                return Views.ADMIN_BOOKS_EDIT
            }

            if (bookForm.cover_image?.isEmpty == false && bookForm.cover_image.contentType?.startsWith("image/") == false) {
                bindingResult.rejectValue("cover_image", "cover_image.wrong.type", "Cover must be image")
                addPubCatAndWriter(model)
                addBookAttribute(book, model)
                return Views.ADMIN_BOOKS_EDIT
            }

            val category = categoryRepository.findById(bookForm.category_id!!).get()
            val publisher = publisherRepository.findById(bookForm.publisher_id!!).get()
            val writers = writerRepository.findAllById(bookForm.writer_ids!!)

            val url = if (bookForm.cover_image?.isEmpty == false) {
                s3Service.save(bookForm.cover_image) ?: throw java.lang.Exception("Failed to upload image")
            } else {
                book.cover_image
            }

            book.apply {
                title = bookForm.title!!
                synopsis = bookForm.synopsis!!
                edition = bookForm.edition!!
                publish_year = bookForm.publish_year!!
                isbn = bookForm.isbn!!
                cover_image = url
                this.category = category
                this.publisher = publisher
                this.writers = writers
                updated_at = LocalDateTime.now()
            }

            bookRepository.save(book)
        } catch (e: Exception) {
            return "redirect:/admin/books/edit/$id?error=Book edit failed"
        }

        return "redirect:/admin/books?success=Book updated successfully"
    }

    private fun addBookAttribute(book: Book, model: Model) {
        model.addAttribute("bookId", book.id)
        model.addAttribute("bookTitle", book.title)
        model.addAttribute("bookImage", book.cover_image)
    }
}