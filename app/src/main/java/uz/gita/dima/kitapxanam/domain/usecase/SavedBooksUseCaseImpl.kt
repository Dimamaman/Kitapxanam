package uz.gita.dima.kitapxanam.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.repository.SavedBooksRepository
import javax.inject.Inject

class SavedBooksUseCaseImpl @Inject constructor(
    private val savedBooksRepository: SavedBooksRepository
): SavedBooksUseCase {
    override fun getSavedBooks(context: Context): Flow<Result<List<BookData>>>  = savedBooksRepository.getSavedBooks(context)
}