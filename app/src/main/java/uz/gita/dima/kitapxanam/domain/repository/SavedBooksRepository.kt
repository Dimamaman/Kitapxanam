package uz.gita.dima.kitapxanam.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.dima.kitapxanam.data.model.BookData

interface SavedBooksRepository {
     fun getSavedBooks(context: Context): Flow<Result<List<BookData>>>
}