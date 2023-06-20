package uz.gita.dima.kitapxanam.presenter.sreens.info

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.databinding.ScreenInfoBinding
import uz.gita.dima.kitapxanam.presenter.sreens.info.viewmodel.InfoViewModel
import uz.gita.dima.kitapxanam.presenter.sreens.info.viewmodel.InfoViewModelImpl
import java.io.File

@AndroidEntryPoint
class InfoScreen: Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val viewModel:InfoViewModel by viewModels<InfoViewModelImpl>()

    private val args: InfoScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.INVISIBLE
        viewModel.progressLiveData.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(context, "Juklenip atir⚡...", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.VISIBLE
            }else{
                Toast.makeText(context, "Juklendi🌩️...", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.INVISIBLE
                binding.downloadBtn.animate()
                    .translationX(500f)
                    .setDuration(2000)
                    .start()
            }
        }
        binding.apply {
            val data = args.book

            if (File(context?.filesDir, "${args.book.reference}.pdf").exists()){
                downloadBtn.setImageResource(R.drawable.done)
                linearLayout.visibility = View.VISIBLE
                downloadBtn.isClickable = false
                binding.downloadBtn.animate()
                    .translationX(500f)
                    .setDuration(0)
                    .start()
            }else{
                linearLayout.visibility = View.GONE
                downloadBtn.isClickable = true
            }

            txtTitle.text = data.name
            txtYear.text = "${data.year}din' bazasi"
            txtDate.text = "${data.date} qosildi"
            if (data.klass>= 5.toString()){
                txtKlass.text = "${data.klass} ushun"
            }else{
                txtKlass.text = "${data.klass}-ushun"
            }
            txtDescription.text = data.description

            Glide
                .with(requireContext())
                .load(data.image_url)
                .into(bookImage)

            downloadBtn.setOnClickListener {
                viewModel.downloadBook(requireContext(), data)
            }


            linearLayout.setOnClickListener {
                val action = InfoScreenDirections.actionInfoScreenToReadBookScreen(data)
                findNavController().navigate(action)
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            viewModel.fileDownloadedLiveData.observe(viewLifecycleOwner) {
                downloadBtn.setImageResource(R.drawable.done)
                linearLayout.visibility = View.VISIBLE
            }

            viewModel.errorDownloadLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}