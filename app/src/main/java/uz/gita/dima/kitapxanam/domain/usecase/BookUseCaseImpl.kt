package uz.gita.dima.kitapxanam.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.data.model.MessageData
import uz.gita.dima.kitapxanam.data.model.ProfileData
import uz.gita.dima.kitapxanam.data.model.TypeData
import uz.gita.dima.kitapxanam.domain.repository.BookRepository
import javax.inject.Inject

class BookUseCaseImpl @Inject constructor(
    private val repository: BookRepository
) : BookUseCase {

    override suspend fun getAllBooks(): Result<List<TypeData>> = repository.getAllBooks()

    override fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>> =
        repository.downloadBook(context, data)

//    override fun getNewBooks(): List<BookData> = repository.getNewBooks()
//
//    override fun gerRecommendBooks(): List<BookData> = repository.gerRecommendBooks()
//
//    override fun getFullBookList(): List<BookData> = repository.getFullBookList()

    override fun getProfileInformation(): Flow<Result<ProfileData>> =
        repository.getProfileInformation()

    override fun addComment(message: MessageData): Flow<Result<String>> =
        repository.addComment(message)

    override fun search(text: String): List<BookData> = repository.getLikeBooks(text)

}