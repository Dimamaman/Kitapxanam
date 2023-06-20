package uz.gita.dima.kitapxanam.presenter.sreens.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.dima.kitapxanam.data.model.TypeData
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
) : ViewModel(), HomeViewModel {
    override val recommendedBooksLiveData = MutableLiveData<List<TypeData>>()
    override val onProgress = MutableLiveData<Boolean>()
    override val onException = MutableLiveData<Boolean>()
    override val onExceptionString = MutableLiveData<String>()

    override fun getAllBooks() {
        onProgress.value = true
        viewModelScope.launch {
            useCase.getAllBooks()
                .onSuccess {
                    it.sortedBy { type ->
                        type.id
                    }
                    recommendedBooksLiveData.value = it
                    onProgress.value = false
                    onException.value = false
                }
                .onFailure {
                    onProgress.value = false
                    onException.value = true
                    onExceptionString.value = it.message
                }
        }
    }
}
