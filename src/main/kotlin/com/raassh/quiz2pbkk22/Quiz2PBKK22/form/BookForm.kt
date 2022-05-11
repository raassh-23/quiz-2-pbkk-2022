package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.IsImage
import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.IsNumeric
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class BookForm(
    @field:NotNull
    @field:NotEmpty
    val title: String? = null,

    @field:NotNull
    @field:NotEmpty
    val synopsis: String? = null,

    @field:NotNull
    @field:Min(value = 1, message="must be equal or greater than 1")
    val edition: Int? = null,

    @field:NotNull
    @field:Min(value = 1900, message="must be equal or greater than 1900")
    @field:Max(value = 2022, message="must be equal or less than 2022")
    val publish_year: Int? = null,

    @field:NotNull
    @field:NotEmpty
    @IsNumeric
    val isbn: String? = null,

    @field:NotNull
    @IsImage
    val cover_image: MultipartFile? = null,

    @field:NotNull
    @field:Min(value = 1, message = "must pick a category")
    val category_id: Long? = null,

    @field:NotNull
    @field:Min(value = 1, message = "must pick a publisher")
    val publisher_id: Long? = null,

    @field:NotNull
    @field:NotEmpty
    val writer_ids: List<Long>? = null,
)