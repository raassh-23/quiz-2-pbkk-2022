package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import com.raassh.quiz2pbkk22.Quiz2PBKK22.validation.ValidEmail
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PublisherForm (
    @NotNull
    @NotEmpty
    val name: String? = null,

    @NotNull
    @NotEmpty
    val address: String? = null,

    @NotNull
    @NotEmpty
    @ValidEmail
    val email: String? = null,

    @NotNull
    @NotEmpty
    val phone: String? = null,

    @NotNull
    val image: MultipartFile? = null,
)