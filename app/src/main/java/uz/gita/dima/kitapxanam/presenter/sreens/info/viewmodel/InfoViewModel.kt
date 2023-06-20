package uz.gita.dima.kitapxanam.presenter.sreens.info.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.dima.kitapxanam.data.model.BookData

interface InfoViewModel {
    val progressLiveData: LiveData<Boolean>
    val errorDownloadLiveData: LiveData<String>
    val fileDownloadedLiveData: LiveData<BookData>

    fun downloadBook(context: Context, data: BookData)
}