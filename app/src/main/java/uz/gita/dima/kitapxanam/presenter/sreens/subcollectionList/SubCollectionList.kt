package uz.gita.dima.kitapxanam.presenter.sreens.subcollectionList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.databinding.ScreenSubcollectionListBinding
import uz.gita.dima.kitapxanam.presenter.adapters.SearchAdapter
import uz.gita.dima.kitapxanam.presenter.sreens.search.SearchDirections

@AndroidEntryPoint
class SubCollectionList : Fragment(R.layout.screen_subcollection_list) {
    private val binding by viewBinding(ScreenSubcollectionListBinding::bind)
    private val args : SubCollectionListArgs by navArgs()
    private val adapter: SearchAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter

        binding.txtSubjectName.text = args.typedData.title.substring(0,7)
        adapter.submitList(args.typedData.list)

        adapter.setItemClickListener {
            val action = SubCollectionListDirections.actionSubCollectionListToInfoScreen(it)
            findNavController().navigate(action)
        }

        adapter.setItemClickListener {
            val action = SubCollectionListDirections.actionSubCollectionListToInfoScreen(it)
            findNavController().navigate(action)
        }
    }
}