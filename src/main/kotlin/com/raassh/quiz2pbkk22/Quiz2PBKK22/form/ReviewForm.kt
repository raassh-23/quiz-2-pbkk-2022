package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ReviewForm(
    @NotNull
    @field:Min(value = 1, message="must be equal or greater than 1")
    @field:Max(value = 10, message="must be equal or less than 10")
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
