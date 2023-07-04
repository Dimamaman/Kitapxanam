package uz.gita.dima.kitapxanam.presenter.sreens.search.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.dima.kitapxanam.data.model.BookData

interface SearchViewModel {
    val bookList: LiveData<List<BookData>>
    val openReadBook: LiveData<Unit>
    fun search(query: String)
}