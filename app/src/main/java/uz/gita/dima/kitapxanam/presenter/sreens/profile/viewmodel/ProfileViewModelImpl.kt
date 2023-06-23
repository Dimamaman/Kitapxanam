package uz.gita.dima.kitapxanam.presenter.sreens.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.dima.kitapxanam.data.model.MessageData
import uz.gita.dima.kitapxanam.data.model.ProfileData
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
) : ViewModel(), ProfileViewModel {
    override fun getOwnerInfo() {
        useCase.getProfileInformation().onEach {
            it.onSuccess { info ->
                profileLiveData.value = info
                onException.value = false
            }
            it.onFailure { exeption ->
                onExceptionString.value = exeption.message
                onException.value = true
            }
        }.launchIn(viewModelScope)
    }

    override fun addComment(message: MessageData) {
        onProgress.value = true
        useCase.addComment(message).onEach {
            it.onSuccess { xabar ->
                onProgress.value = false
                messageString.value = xabar
            }
            it.onFailure { error ->
                onProgress.value = false
                messageString.value = "Mag'lumat jiberiwde qa'telik :( \n ${error.message}"
            }
        }.launchIn(viewModelScope)
    }

    override val profileLiveData = MutableLiveData<ProfileData>()
    override val onProgress = MutableLiveData<Boolean>()
    override val onException = MutableLiveData<Boolean>()
    override val onExceptionString = MutableLiveData<String>()
    override val messageString = MutableLiveData<String>()
    override var boolean = false
}