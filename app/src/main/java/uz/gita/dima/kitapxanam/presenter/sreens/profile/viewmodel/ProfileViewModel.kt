package uz.gita.dima.kitapxanam.presenter.sreens.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.dima.kitapxanam.data.model.MessageData
import uz.gita.dima.kitapxanam.data.model.ProfileData

interface ProfileViewModel {
    val profileLiveData: LiveData<ProfileData>
    fun getOwnerInfo()
    fun addComment(message: MessageData)

    val onProgress: LiveData<Boolean>
    val onException: LiveData<Boolean>
    val onExceptionString: LiveData<String>

    val messageString: MutableLiveData<String>

    var boolean:Boolean
}