package uz.gita.dima.kitapxanam.presenter.sreens.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.databinding.ScreenSavedBinding
import uz.gita.dima.kitapxanam.presenter.adapters.SavedAdapter
import uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel.SavedViewModel
import uz.gita.dima.kitapxanam.presenter.sreens.saved.viewmodel.impl.SavedViewModelImpl
import uz.gita.dima.kitapxanam.util.log
import uz.gita.dima.kitapxanam.util.toasT
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class SavedScreen: Fragment(R.layout.screen_saved) {
    private val binding by viewBinding(ScreenSavedBinding::bind)
    private val viewModel: SavedViewModel by viewModels<SavedViewModelImpl>()
    @Inject
    lateinit var savedAdapter: SavedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllData(requireContext())

        viewModel.progress.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    progress.visibility = View.VISIBLE
                    imgNoBooks.visibility = View.GONE
                    txtNoBookTitle.visibility = View.GONE
                }
            } else {
                binding.progress.visibility = View.GONE
            }
        }

        savedAdapter.setClickListener {
            val action = SavedScreenDirections.actionSavedScreenToReadBookScreen(it)
            findNavController().navigate(action)
        }

        savedAdapter.setDeleteClickListener {
            viewModel.showDeleteDialog(requireContext(),it)
        }

        binding.apply {
            val layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
            layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            binding.recycler.layoutManager = layoutManager
            binding.recycler.itemAnimator = DefaultItemAnimator()
            recycler.adapter = savedAdapter
        }

        viewModel.booksData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    imgNoBooks.visibility = View.VISIBLE
                    txtNoBookTitle.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            } else {
                binding.apply {
                    imgNoBooks.visibility = View.GONE
                    txtNoBookTitle.visibility = View.GONE
                }
            }
            savedAdapter.submitList(it)
        }

        viewModel.errorData.observe(viewLifecycleOwner) {
            toasT(it)
        }
    }
}