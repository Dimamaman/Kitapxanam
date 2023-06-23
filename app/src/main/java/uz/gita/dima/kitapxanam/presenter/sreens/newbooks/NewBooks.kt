package uz.gita.dima.kitapxanam.presenter.sreens.newbooks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.databinding.ScreenNewBooksBinding

class NewBooks: Fragment(R.layout.screen_new_books) {
    private val binding: ScreenNewBooksBinding by viewBinding(ScreenNewBooksBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}