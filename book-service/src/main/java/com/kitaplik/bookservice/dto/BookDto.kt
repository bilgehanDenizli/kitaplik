package com.kitaplik.bookservice.dto

import com.kitaplik.bookservice.model.Book


data class BookDto @JvmOverloads constructor(
        val id : String? = null,
        val title: String,
        val author: String,
        val bookYear: Int,
        val pressName: String,
        val isbn: String
){
    companion object {
        @JvmStatic
        fun convert(from: Book): BookDto{
            return BookDto(
                    from.id,/*from.id?.let { BookIdDto.convert(it,from.isbn) }*/
                    from.title,
                    from.author,
                    from.bookYear,
                    from.pressName,
                    from.isbn
            )
        }
    }
}