package uz.gita.dima.kitapxanam.presenter.sreens.info

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.dima.kitapxanam.BuildConfig
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.databinding.ScreenInfoBinding
import uz.gita.dima.kitapxanam.presenter.sreens.info.viewmodel.InfoViewModel
import uz.gita.dima.kitapxanam.presenter.sreens.info.viewmodel.InfoViewModelImpl
import java.io.File

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val viewModel: InfoViewModel by viewModels<InfoViewModelImpl>()

    private val args: InfoScreenArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.INVISIBLE

        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Juklenip atir⚡...", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.VISIBLE
            } else {
                Toast.makeText(context, "Juklendi🌩️...", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.INVISIBLE
                binding.downloadBtn.animate()
                    .translationX(500f)
                    .setDuration(2000)
                    .start()
            }
        }
        binding.imgShare.setOnClickListener {
//            val file = File(context?.filesDir, "${args.book.reference}.pdf").absolutePath
//            val uri = FileProvider.getUriForFile(
//                requireContext(),
//                "uz.gita.dima.kitapxanam.presenter.sreens.info.fileprovider",
//                file
//            )
//            val share = Intent().setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_STREAM, uri)
//                .setType("application/pdf")
//            startActivity(share)

//            val intent = Intent()
//            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.putExtra(
//                Intent.EXTRA_STREAM,
//                uriFromFile(
//                    requireContext(),
//                    File(
//                        requireActivity().getExternalFilesDir(null)?.absolutePath.toString(),
//                        "${args.book.reference}.pdf"
//                    )
//                )
//            )
//            shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//            shareIntent.type = "application/pdf"
//            startActivity(shareIntent)


            val pdfFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), File(context?.filesDir, "${args.book.reference}.pdf").path)

            if (pdfFile.exists()) {
                // Do something with the file
                Log.d("TTTT","DDDDDDD AWA")
            } else {
                // File does not exist
                Log.d("TTTT","YYYYYAAAQQ   ${File(context?.filesDir, "${args.book.reference}.pdf").path} ")
            }

            val intent = Intent()
            val aName = intent.getStringExtra("iName")
            val shareIntent = Intent(Intent.ACTION_SEND)
            val file =
                File(requireActivity().getExternalFilesDir("${args.book.reference}.pdf")?.absolutePath.toString(), "$aName")
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }

            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.type = "application/pdf"

            startActivity(Intent.createChooser(shareIntent, "Share PDF"))


        }

        binding.apply {
            val data = args.book

            if (File(context?.filesDir, "${args.book.reference}.pdf").exists()) {
                downloadBtn.setImageResource(R.drawable.done)
                linearLayout.visibility = View.VISIBLE
                downloadBtn.isClickable = false
                binding.downloadBtn.animate()
                    .translationX(500f)
                    .setDuration(0)
                    .start()

                imgShare.visibility = View.VISIBLE
            } else {
                linearLayout.visibility = View.GONE
                downloadBtn.isClickable = true
            }

            txtTitle.text = data.name
            txtYear.text = "${data.year}din' bazasi"
            txtDate.text = "${data.date} qosildi"
            if (data.klass >= 5.toString()) {
                txtKlass.text = "${data.klass} ushun"
            } else {
                txtKlass.text = "${data.klass}-ushun"
            }
            txtDescription.text = data.description

            Glide
                .with(requireContext())
                .load(data.imageUrl)
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
                imgShare.visibility = View.VISIBLE
                linearLayout.visibility = View.VISIBLE
            }

            viewModel.errorDownloadLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uriFromFile(context: Context, file: File): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )
        } else {
            Uri.fromFile(file)
        }
    }

    private fun sharePDF(file: File) {
        //  val file = File(pdfFilePath)
        val uri = Uri.fromFile(file)
        val URI = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, URI)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(Intent.createChooser(intent, "Share PDF"))
    }


//    private fun searchDir(dir: File) {
//        val pdfPattern = ".pdf"
//        val FileList = dir.listFiles()
//        if (FileList != null) {
//            for (i in FileList.indices) {
//                if (FileList[i].isDirectory) {
//                    searchDir(FileList[i])
//                } else {
//                    if (FileList[i].name.endsWith(pdfPattern)) {
//                        //here you have that file.
//                    }
//
//                    Log.d("HHH"," PDF  ->  ${FileList[i].name}")
//                }
//            }
//        }
//    }

}