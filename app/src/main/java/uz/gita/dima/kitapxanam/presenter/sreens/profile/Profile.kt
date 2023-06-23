package uz.gita.dima.kitapxanam.presenter.sreens.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.data.model.MessageData
import uz.gita.dima.kitapxanam.databinding.ScreenProfileBinding
import uz.gita.dima.kitapxanam.presenter.sreens.profile.viewmodel.ProfileViewModel
import uz.gita.dima.kitapxanam.presenter.sreens.profile.viewmodel.ProfileViewModelImpl
import java.net.NetworkInterface

@AndroidEntryPoint
class Profile : Fragment(R.layout.screen_profile) {
    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getOwnerInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onProgress.observe(viewLifecycleOwner) {
            if (it) {
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
                binding.jiberildi.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.jiberildi.visibility = View.GONE
                    binding.jiberiw.visibility = View.VISIBLE
                }, 1000)
            }
        }




        viewModel.onException.observe(viewLifecycleOwner) {
            if (it) {
                binding.emptyList.visibility = View.VISIBLE
            } else {
                binding.emptyList.visibility = View.GONE
            }
        }
        viewModel.onExceptionString.observe(viewLifecycleOwner) {
            binding.imgError.setImageResource(R.drawable.emptylist)
            binding.txtError.text = it
        }

        viewModel.profileLiveData.observe(viewLifecycleOwner) { data ->
            binding.profilDescription.text = data.description
            binding.profilTitle.text = data.title

            Glide.with(requireContext())
                .load(data.image_url)
                .into(binding.profileImage)
            binding.apply {
                profilTelegram.setOnClickListener { view ->
                    val uri = Uri.parse(data.telegram)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                profilInstagram.setOnClickListener { view ->
                    val uri = Uri.parse(data.instagram)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

                profileSendMessage.setOnClickListener {
                    if (editTextText.text.isNullOrEmpty() || editTextText2.text.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Mag'luwmatlardi to'liq kiriting",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    val mac = getMacAddress()
                    val name = editTextText.text.toString()
                    val comment = editTextText2.text.toString()

                    binding.jiberiw.visibility = View.GONE
                    viewModel.addComment(MessageData(mac, name, comment))
                    editTextText.text!!.clear()
                    editTextText2.text!!.clear()
                    viewModel.boolean = true
                }
            }
        }


        binding.apply {
            if (viewModel.boolean) {
                editTextText.isEnabled = false
                editTextText2.isEnabled = false
                editTextText.isCursorVisible = false
                editTextText2.isCursorVisible = false
                profileSendMessage.visibility = View.GONE
            }
        }
    }

    private fun getMacAddress(): String {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()

        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val macAddress = networkInterface.hardwareAddress

            if (macAddress != null && macAddress.isNotEmpty()) {
                val stringBuilder = StringBuilder()

                for (byte in macAddress) {
                    stringBuilder.append(String.format("%02X:", byte))
                }

                if (stringBuilder.isNotEmpty()) {
                    stringBuilder.deleteCharAt(stringBuilder.length - 1)
                }

                return stringBuilder.toString()
            }
        }
        return "${Build.BOOTLOADER} ham  ${Build.FINGERPRINT}  qurilma..."
    }
}