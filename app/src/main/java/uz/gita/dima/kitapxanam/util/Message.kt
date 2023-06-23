package uz.gita.dima.kitapxanam.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toasT(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun log(message: String) {
    Log.d("AAA", message)
}

fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(this.context).inflate(resId, this, false)
}

object Constant {
    const val SHARED_PREF = "shared_pref"
    const val BOOK_NAME = "book_name"
    const val SAVED_PAGE = "saved_page"
    const val TOTAL_PAGE = "total_page"
}