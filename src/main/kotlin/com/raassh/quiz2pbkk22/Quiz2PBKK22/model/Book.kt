package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "books")
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String = "",
    val synopsis: String = "",
    val edition: Int = 0,
    val publish_year: Int = 0,
    val cover_image: String = "",
    val isbn: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "publisher_id")
    val publisher: Publisher? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "book_writer",
        joinColumns = [JoinColumn(name = "book_id", foreignKey = ForeignKey(name = "book_writer_book_id_foreign"))],
        inverseJoinColumns = [JoinColumn(
            name = "writer_id",
            foreignKey = ForeignKey(name = "book_writer_writer_id_foreign")
        )]
    )
    val writers: MutableList<Writer> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "category_id")
    val category: Category? = null,

    @OneToMany(mappedBy = "book")
    val reviews: List<Review>? = null
) {
    fun rating(): Float? {
        if (reviews == null || reviews.isEmpty()) {
            return null
        }

        var sum = 0f

        reviews.forEach {
            sum += it.rating
        }

        return sum / reviews.size
    }

    fun formattedRating(decimalPlaces: Int = 2) = String.format("%.${decimalPlaces}f", rating())

    fun writersName() = StringBuilder().apply {
        writers.forEachIndexed { i, writer ->
            append(writer.name)

            if (i < writers.size - 1) {
                append(", ")
            }
        }
    }.toString()
}