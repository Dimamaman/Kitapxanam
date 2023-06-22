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
) : Serializable {
//    fun toEntity(unv_title: String = "", logo: String = ""): BookEntity {
//        return BookEntity(
//            id = id,
//            name = name,
//            klass = klass,
//            year = year,
//            date = date,
//            reference = reference,
//            description = description,
//            logo = logo,
//            pdf_url = pdf_url,
//            image_url = image_url
//        )
//    }
}
