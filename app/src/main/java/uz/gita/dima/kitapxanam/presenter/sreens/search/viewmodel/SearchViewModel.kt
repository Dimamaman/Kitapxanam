package uz.gita.dima.kitapxanam.presenter.sreens.search.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.SharedFlow
import uz.gita.dima.kitapxanam.data.model.BookData

interface SearchViewModel {
    val bookSharedFlow: SharedFlow<List<BookData>>
    val openReadBook: LiveData<Unit>
    fun search(query: String)
}