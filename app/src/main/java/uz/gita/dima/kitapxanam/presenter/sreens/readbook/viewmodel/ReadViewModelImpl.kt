package uz.gita.dima.kitapxanam.presenter.sreens.readbook.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class ReadViewModelImpl @Inject constructor(
    private val useCase: BookUseCase
): ViewModel(), ReadViewModel {

    override fun saveBookAsRead(data: BookData, currentPage: Int) {
        Log.d("TTT", "Saving Book in ReadViewModel: $data")
    }
}
