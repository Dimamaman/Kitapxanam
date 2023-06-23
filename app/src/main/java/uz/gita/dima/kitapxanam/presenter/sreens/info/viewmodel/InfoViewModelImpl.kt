package uz.gita.dima.kitapxanam.presenter.sreens.info.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
) : ViewModel(), InfoViewModel {

    override val progressLiveData = MutableLiveData<Boolean>()
    override val errorDownloadLiveData = MutableLiveData<String>()
    override val fileDownloadedLiveData = MutableLiveData<BookData>()
    override val readyOpen = MutableSharedFlow<Boolean>()

    override fun downloadBook(context: Context, data: BookData) {
        progressLiveData.value = true
        useCase.downloadBook(context, data).onEach { result ->
            result.onSuccess {
                fileDownloadedLiveData.value = it
                progressLiveData.value = false
                readyOpen.emit(true)
            }
            result.onFailure {
                errorDownloadLiveData.value = it.message
                progressLiveData.value = false
                readyOpen.emit(false)
            }
        }.launchIn(viewModelScope)
    }
}