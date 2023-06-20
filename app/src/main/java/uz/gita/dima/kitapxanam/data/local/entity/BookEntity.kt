package uz.gita.dima.kitapxanam.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.dima.kitapxanam.data.model.BookData

@Entity(tableName = "Books")
data class BookEntity(
    var date: String,
    var description: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    var image_url:String,
    var isActive : Boolean,
    var klass: String,
    var name: String,
    var pdf_url:String,
    var reference: String,
    var year: String,
    var isnewbookread: Int = 0,
    var logo: String,
) {
//    fun toBookData(): BookData {
//        return BookData(
//            date,
//            description,
//            id,
//            name,
//            true,
//            pdf_url,
//            reference,
//            year,
//            reference,
//            image_url
//        )
//    }

}
