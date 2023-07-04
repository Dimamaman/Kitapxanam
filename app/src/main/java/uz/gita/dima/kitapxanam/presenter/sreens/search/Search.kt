package uz.gita.dima.kitapxanam.presenter.sreens.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.databinding.ScreenSearchBinding
import uz.gita.dima.kitapxanam.presenter.adapters.SearchAdapter
import uz.gita.dima.kitapxanam.presenter.sreens.search.viewmodel.SearchViewModel
import uz.gita.dima.kitapxanam.presenter.sreens.search.viewmodel.SearchViewModelImpl

@AndroidEntryPoint
class Search : Fragment(R.layout.screen_search) {
    private val binding by viewBinding(ScreenSearchBinding::bind)

    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl>()

    private val adapter: SearchAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchAdapter()
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.listSearchBooks.adapter = adapter

//        viewModel.bookSharedFlow.onEach {
//            if (it.isEmpty()) {
//                binding.apply {
//                    empty.visibility = View.VISIBLE
//                    listSearchBooks.visibility = View.INVISIBLE
//                }
//            } else {
//                binding.listSearchBooks.visibility = View.VISIBLE
//                binding.empty.visibility = View.INVISIBLE
//                adapter.submitList(it)
//            }
//        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.bookList.observe(viewLifecycleOwner, list)

        binding.tvCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        adapter.setItemClickListener {
            val action = SearchDirections.actionSearch2ToInfoScreen(it)
            findNavController().navigate(action)
        }

        binding.inputBook.textChanges().onEach {
            viewModel.search(it.toString())
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private val list = Observer<List<BookData>> {
        if (it.isEmpty()) {
            binding.empty.visibility = View.VISIBLE
            binding.listSearchBooks.visibility = View.INVISIBLE
        } else {
            binding.empty.visibility = View.GONE
            binding.listSearchBooks.visibility = View.VISIBLE
            adapter.submitList(it)
        }
    }
}