package uz.gita.dima.kitapxanam.presenter.sreens.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModelImpl @Inject constructor(
    private val bookUseCase: BookUseCase
): SearchViewModel, ViewModel(){
    override val bookSharedFlow  = MutableSharedFlow<List<BookData>>()

    override val openReadBook: LiveData<Unit> = MutableLiveData()

    override fun search(query: String) {

        viewModelScope.launch {
            bookUseCase.search(query).onSuccess {
            Log.d("PPP","List -> $it")
                bookSharedFlow.emit(it)
            }.onFailure {

            }
        }
    }
}