package com.kitaplik.libraryservice.dto


data class BookDto @JvmOverloads constructor(
        val id : String? = null,
        val title: String? = "",
        val author: String? = "",
        val bookYear: Int? = 0,
        val pressName: String? = "",
        val isbn: String? = ""
)