package uz.gita.dima.kitapxanam.presenter.sreens.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
    lateinit var favoriteAdapter: SavedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllData(requireContext())

        favoriteAdapter.setClickListener {
            Log.d("AAA", it.toString())
//            val action = FavoriteFragmentDirections.actionFavoriteFragmentToReadBookFragment(0,it.page.toInt(),it.bookName)
//            findNavController().navigate(action)

//            parentFragment?.findNavController(requireActivity(),R.id.action_favoriteFragment_to_readBookFragment)

//            Navigation.findNavController(requireActivity(),R.id.action_favoriteFragment_to_readBookFragment)
//            (FragmentComponentManager.findActivity(view.context) as Activity).findNavController(R.id.action_favoriteFragment_to_readBookFragment)
        }

        favoriteAdapter.setDeleteClickListener {
            val file = File(requireContext().filesDir, it.name)
            val deleted = if (file.exists()) file.delete() else false

            if (deleted){
                viewModel.getAllData(requireContext())
                toasT("Book deleted")
            }
            else log("File not found")
            favoriteAdapter.notifyDataSetChanged()
        }

        binding.apply {
            recycler.layoutManager = GridLayoutManager(requireContext(), 2)
            recycler.adapter = favoriteAdapter
        }

        viewModel.booksData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    imgNoBooks.visibility = View.VISIBLE
                    txtNoBookTitle.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    imgNoBooks.visibility = View.GONE
                    txtNoBookTitle.visibility = View.GONE
                }
            }
            favoriteAdapter.submitList(it)
        }

        viewModel.errorData.observe(viewLifecycleOwner) {
            toasT(it)
        }
    }
}