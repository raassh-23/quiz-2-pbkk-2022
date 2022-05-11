package com.raassh.quiz2pbkk22.Quiz2PBKK22.form

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CategoryForm (
    @field:NotNull
    @field:NotEmpty
    val name: String? = null
)