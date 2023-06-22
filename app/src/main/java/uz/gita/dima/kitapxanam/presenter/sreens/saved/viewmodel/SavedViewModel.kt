package uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.dima.kitapxanam.data.model.BookData

interface SavedViewModel {
    val errorData: LiveData<String>
    val booksData: LiveData<List<BookData>>

    fun getAllData(context: Context)
}