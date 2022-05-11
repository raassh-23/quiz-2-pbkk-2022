package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ReviewForm(
    @NotNull
    @NotEmpty
    val rating: Int? = null,

    @NotNull
    @NotEmpty
    val review: String? = null,

    @NotNull
    @NotEmpty
    val user_id: Long? = null,

    @NotNull
    @NotEmpty
    val book_id: Long? = null,
)
