package uz.gita.dima.kitapxanam.presenter.sreens.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.dima.kitapxanam.data.local.sharedPref.SharedPref
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.data.model.TypeData
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase: BookUseCase,
    private val sharedPref: SharedPref
) : ViewModel(), HomeViewModel {
    override val recommendedBooksLiveData = MutableLiveData<List<TypeData>>()
    override val onProgress = MutableLiveData<Boolean>()
    override val onException = MutableLiveData<Boolean>()
    override val onExceptionString = MutableLiveData<String>()
    override val book = MutableLiveData<BookData>()

    override fun getAllBooks() {
        onProgress.value = true
        viewModelScope.launch {
            useCase.getAllBooks()
                .onSuccess {
                    it.forEach { typeData ->
                        typeData.list.forEach { bookData ->
                            if (bookData.klass == sharedPref.klass && bookData.name == sharedPref.bookName) {
                                Log.d("Muhammadali","Book -> $bookData")
                                book.value = bookData
                            }
                        }
                    }

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
