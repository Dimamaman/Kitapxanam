package uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.dima.kitapxanam.data.model.BookData

interface SavedViewModel {
    val errorData: LiveData<String>
    val booksData: Flow<List<BookData>>
    val progress: LiveData<Boolean>

    fun getAllData(context: Context)
    fun showDeleteDialog(context: Context, book: BookData)
}