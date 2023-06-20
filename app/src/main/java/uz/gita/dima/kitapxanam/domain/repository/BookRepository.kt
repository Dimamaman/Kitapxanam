package uz.gita.dima.kitapxanam.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.data.model.MessageData
import uz.gita.dima.kitapxanam.data.model.ProfileData
import uz.gita.dima.kitapxanam.data.model.TypeData

interface BookRepository {

    suspend fun getAllBooks(): Result<List<TypeData>>

    fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>>

//    fun getNewBooks(): List<BookData>

//    fun gerRecommendBooks(): List<BookData>

//    fun getFullBookList(): List<BookData>

    fun getProfileInformation(): Flow<Result<ProfileData>>

    fun addComment(message: MessageData): Flow<Result<String>>

    fun getLikeBooks(like: String): List<BookData>

}