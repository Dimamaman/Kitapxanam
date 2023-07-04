package uz.gita.dima.kitapxanam.presenter.sreens.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModelImpl @Inject constructor(
    private val bookUseCase: BookUseCase
): SearchViewModel, ViewModel(){
    override val bookList = MutableLiveData<List<BookData>>()

    override val openReadBook: LiveData<Unit> = MutableLiveData()

    override fun search(query: String) {

        viewModelScope.launch {
            bookUseCase.search(query).onSuccess {
                bookList.value = it
            }
        }
    }
}