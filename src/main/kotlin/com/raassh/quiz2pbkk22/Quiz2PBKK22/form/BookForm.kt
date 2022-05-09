package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.IsNumeric
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class BookForm(
    @NotNull
    @NotEmpty
    val title: String? = null,

    @NotNull
    @NotEmpty
    val synopsis: String? = null,

    @NotNull
    @NotEmpty
    @Min(value = 1, message="must be equal or greater than 1")
    val edition: Int? = null,

    @NotNull
    @NotEmpty
    @Min(value = 1900, message="must be equal or greater than 1900")
    @Max(value = 2022, message="must be equal or less than 2022")
    val publish_year: Int? = null,

    @NotNull
    @NotEmpty
    @IsNumeric
    val isbn: String? = null,

    @NotNull
    val cover_image: MultipartFile? = null,

    @NotNull
    @NotEmpty
    val category_id: Long? = null,

    @NotNull
    @NotEmpty
    val publisher_id: Long? = null,

    @NotNull
    @NotEmpty
    val writer_ids: List<Long>? = null,
)