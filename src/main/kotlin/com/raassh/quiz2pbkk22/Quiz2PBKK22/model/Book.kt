package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "books")
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var title: String = "",
    var synopsis: String = "",
    var edition: Int = 0,
    var publish_year: Int = 0,
    var cover_image: String = "",
    var isbn: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    var updated_at: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    var publisher: Publisher? = null,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "book_writer",
        joinColumns = [JoinColumn(name = "book_id", foreignKey = ForeignKey(name = "book_writer_book_id_foreign"))],
        inverseJoinColumns = [JoinColumn(
            name = "writer_id",
            foreignKey = ForeignKey(name = "book_writer_writer_id_foreign")
        )]
    )
    var writers: MutableList<Writer> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null,

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
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