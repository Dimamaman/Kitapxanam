package uz.gita.dima.kitapxanam.presenter.sreens.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.data.local.sharedPref.SharedPref
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.databinding.ScreenHomeBinding
import uz.gita.dima.kitapxanam.presenter.adapters.TypeAdapter
import uz.gita.dima.kitapxanam.presenter.sreens.home.viewmodel.HomeViewModel
import uz.gita.dima.kitapxanam.presenter.sreens.home.viewmodel.HomeViewModelImpl
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    private val adapter: TypeAdapter = TypeAdapter()

    lateinit var bookData: BookData

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllBooks()

        if (sharedPref.bookName!!.isEmpty()) {
            binding.constraint.visibility = View.GONE
        } else {
            binding.constraint.visibility = View.VISIBLE
            binding.tvBookName.text = sharedPref.bookName
            binding.klassName.text = sharedPref.klass
            binding.tvBookPage.text = "Bet : ${sharedPref.savedPage}"
        }

        /*requireActivity().addMenuProvider(object : MenuProvider {
            @SuppressLint("DiscouragedApi")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val search = menu.findItem(R.id.appSearchBar)
                search.setIcon(R.drawable.ic_search)
                val searchView = search.actionView as SearchView

                val searchPlateId = searchView.context.resources.getIdentifier(
                    "android:id/search_plate",
                    null,
                    null
                )
                val searchPlate: View = searchView.findViewById(searchPlateId)
                searchPlate.setBackgroundResource(0)
                searchView.isIconifiedByDefault = false

                val magId =
                    searchView.resources.getIdentifier("android:id/search_mag_icon", null, null)
                val magImage: ImageView = searchView.findViewById<View>(magId) as ImageView

                val linearLayoutSearchView = magImage.parent as ViewGroup
                linearLayoutSearchView.removeView(magImage)

                searchView.onActionViewExpanded()
                searchView.queryHint = "Search..."

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
//                            viewModel.searchNote(search = it)
                        }

                        if (newText!!.isEmpty()) {
                            viewModel.getAllBooks()
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner)*/

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_search2)
        }

        viewModel.book.observe(viewLifecycleOwner, book)

        binding.constraint.setOnClickListener {
            val action = HomeScreenDirections.actionHomeScreenToReadBookScreen(bookData)
            findNavController().navigate(action)
        }


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
            binding.txtError.text = "Server yaki internetge baylanisda qa'telik"
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.notifications.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_newBooks)
        }
        adapter.setBookClickListener {
            val action = HomeScreenDirections.actionHomeScreen2ToInfoScreen(it)
            findNavController().navigate(action)
        }

        adapter.setGoToClickListener {
            val action = HomeScreenDirections.actionHomeScreenToSubCollectionList(it)
            findNavController().navigate(action)
        }

        binding.recomendationRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@HomeScreen.adapter
        }

        viewModel.recommendedBooksLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private val book = Observer<BookData> {
        bookData = it
    }
}