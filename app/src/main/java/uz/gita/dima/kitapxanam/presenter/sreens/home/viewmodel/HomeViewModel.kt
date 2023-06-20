package uz.gita.dima.kitapxanam.presenter.sreens.home.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.dima.kitapxanam.data.model.TypeData

interface HomeViewModel {

    fun getAllBooks()

    val recommendedBooksLiveData: LiveData<List<TypeData>>

    val onProgress:LiveData<Boolean>
    val onException:LiveData<Boolean>
    val onExceptionString:LiveData<String>
}
