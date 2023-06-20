package uz.gita.dima.kitapxanam.presenter.sreens.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.databinding.ScreenHomeBinding
import uz.gita.dima.kitapxanam.presenter.adapters.TypeAdapter
import uz.gita.dima.kitapxanam.presenter.sreens.home.viewmodel.HomeViewModel
import uz.gita.dima.kitapxanam.presenter.sreens.home.viewmodel.HomeViewModelImpl

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    private val adapter: TypeAdapter = TypeAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllBooks()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onProgress.observe(viewLifecycleOwner){
            if (it){
                binding.progress.visibility = View.VISIBLE
            }else{
                binding.progress.visibility = View.GONE
            }
        }
        viewModel.onException.observe(viewLifecycleOwner){
            if (it){
                binding.emptyList.visibility = View.VISIBLE
            }else{
                binding.emptyList.visibility = View.GONE
            }
        }
        viewModel.onExceptionString.observe(viewLifecycleOwner){
            binding.imgError.setImageResource(R.drawable.nointernet)
            binding.txtError.text = "Server yoki internetga ulanish xatosi :("
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.notifications.setOnClickListener {
//            findNavController().navigate(R.id.action_homeScreen_to_newBooksList)
        }
        adapter.setBookClickListener {
            val action = HomeScreenDirections.actionHomeScreen2ToInfoScreen(it)
            findNavController().navigate(action)
        }

        adapter.setGoToClickListener {
//            val action = HomeScreenDirections.actionHomeScreenToScreenList(it)
//            findNavController().navigate(action)
        }

        binding.recomendationRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@HomeScreen.adapter
        }

        viewModel.recommendedBooksLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }
}