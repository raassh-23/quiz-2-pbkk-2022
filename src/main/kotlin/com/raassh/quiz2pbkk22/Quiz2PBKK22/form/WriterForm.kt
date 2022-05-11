package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.IsImage
import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.ValidEmail
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class WriterForm (
    @field:NotNull
    @field:NotEmpty
    val name: String? = null,

    @field:NotNull
    @field:NotEmpty
    val address: String? = null,

    @field:NotNull
    @field:NotEmpty
    @ValidEmail
    val email: String? = null,

    @field:NotNull
    @field:NotEmpty
    val phone: String? = null,

    @field:NotNull
    @IsImage
    val image: MultipartFile? = null,
)