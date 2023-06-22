package uz.gita.dima.kitapxanam.data.model

import java.io.Serializable

data class BookData(
    var date: String,
    var description: String,
    val id: Long = 0,
    var imageUrl:String,
    var isActive: Boolean,
    var klass: String,
    var name: String,
    var pdfUrl:String,
    var reference: String,
    var year: String,
) : Serializable
