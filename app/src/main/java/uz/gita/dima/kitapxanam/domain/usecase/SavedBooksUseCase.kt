package uz.gita.dima.kitapxanam.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.dima.kitapxanam.data.model.BookData

interface SavedBooksUseCase {
    fun getSavedBooks(context: Context): Flow<Result<List<BookData>>>
}