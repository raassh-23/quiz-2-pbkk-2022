package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "writers")
class Writer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val email: String = "",
    val phone: String = "",
    val image_url: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime = LocalDateTime.now(),


    @ManyToMany
    @Cascade(value = [CascadeType.DELETE_ORPHAN])
    @JoinTable(
        name = "book_writer",
        joinColumns = [JoinColumn(name = "writer_id", foreignKey = ForeignKey(name = "book_writer_writer_id_foreign"))],
        inverseJoinColumns = [JoinColumn(
            name = "book_id",
            foreignKey = ForeignKey(name = "book_writer_book_id_foreign")
        )]
    )
    val books: List<Book>? = null
)