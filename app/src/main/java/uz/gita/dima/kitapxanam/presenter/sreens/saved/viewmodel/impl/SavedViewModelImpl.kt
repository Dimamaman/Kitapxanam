package uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel.impl

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.usecase.SavedBooksUseCase
import uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel.SavedViewModel
import uz.gita.dima.kitapxanam.util.log
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SavedViewModelImpl @Inject constructor(
    private val savedBooksUseCase: SavedBooksUseCase
) : ViewModel(), SavedViewModel {
    override val errorData = MutableLiveData<String>()
    override val booksData = MutableStateFlow(listOf<BookData>())
    override val progress = MutableLiveData<Boolean>()

    override fun getAllData(context: Context) {
        progress.value = true
//        savedBooksUseCase.getSavedBooks(context).onEach {
//            it.onSuccess {
//                progress.value = false
//                booksData.value = it
//            }
//            it.onFailure {
//                progress.value = false
//                Log.d("RRR", "Error -> ${it.message}")
//                errorData.value = it.message
//            }
//        }.launchIn(viewModelScope)

        viewModelScope.launch {
            savedBooksUseCase.getSavedBooks(context).collect {
                it.onSuccess { list ->
                    progress.value = false
                    booksData.value = list
                }
                it.onFailure {
                    progress.value = false
                    Log.d("RRR", "Error -> ${it.message}")
                    errorData.value = it.message
                }
            }
        }
    }

    override fun showDeleteDialog(context: Context, book: BookData) {
        val file = File(context.filesDir, book.reference + ".pdf")
        val deleted = if (file.exists()) file.delete() else false

        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_custom)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val noBtn: MaterialButton = dialog.findViewById(R.id.cancel_btn)
        val yesBtn: MaterialButton = dialog.findViewById(R.id.okay_btn)
        val titleNote: AppCompatTextView = dialog.findViewById(R.id.title)
        titleNote.text = context.getString(R.string.title, book.name)

        noBtn.setOnClickListener { dialog.dismiss() }

        yesBtn.setOnClickListener {
            if (deleted){
                progress.value = true
                savedBooksUseCase.getSavedBooks(context).onEach {
                    it.onSuccess {
                        progress.value = false
                        booksData.value = it
                    }
                    it.onFailure {
                        progress.value = false
                        Log.d("RRR", "Error -> ${it.message}")
                        errorData.value = it.message
                    }
                }.launchIn(viewModelScope)
                Toast.makeText(context, "Book deleted", Toast.LENGTH_SHORT).show()
            }
            else log("File not found")
            dialog.dismiss()
        }

        dialog.create()
        dialog.show()
    }
}