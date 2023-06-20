package uz.gita.dima.kitapxanam.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.dima.kitapxanam.data.local.dao.BookDao
import uz.gita.dima.kitapxanam.data.local.entity.BookEntity

@Database(entities = [BookEntity::class], version = 3, exportSchema = false)
abstract class BookDatabase:RoomDatabase() {
    abstract fun getBookDao(): BookDao
}