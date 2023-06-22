package uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel.impl

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.usecase.SavedBooksUseCase
import uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel.SavedViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModelImpl @Inject constructor(
    private val savedBooksUseCase: SavedBooksUseCase
) : ViewModel(), SavedViewModel {
    override val errorData = MutableLiveData<String>()
    override val booksData = MutableLiveData<List<BookData>>()

    override fun getAllData(context: Context) {
        savedBooksUseCase.getSavedBooks(context).onEach {
            it.onSuccess {
                booksData.value = it
            }
            it.onFailure {
                Log.d("RRR","Error -> ${it.message}")
                errorData.value = it.message
            }
        }.launchIn(viewModelScope)
    }
}