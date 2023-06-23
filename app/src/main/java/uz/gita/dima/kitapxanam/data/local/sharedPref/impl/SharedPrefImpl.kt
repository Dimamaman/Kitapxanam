package uz.gita.dima.kitapxanam.data.local.sharedPref.impl

import android.content.SharedPreferences
import uz.gita.dima.kitapxanam.data.local.sharedPref.SharedPref
import uz.gita.dima.kitapxanam.util.Constant.BOOK_NAME
import uz.gita.dima.kitapxanam.util.Constant.SAVED_PAGE
import uz.gita.dima.kitapxanam.util.Constant.TOTAL_PAGE
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(private val pref: SharedPreferences) : SharedPref {

    override var bookName: String?
        get() = pref.getString(BOOK_NAME, "")
        set(value) = pref.edit().putString(BOOK_NAME, value).apply()

    override var klass: String?
        get() = pref.getString("KLASS","")
        set(value) { pref.edit().putString("KLASS",value).apply()}

    override var savedPage: Int
        get() = pref.getInt(SAVED_PAGE, 0)
        set(value) = pref.edit().putInt(SAVED_PAGE, value).apply()

    override var totalPage: Int
        get() = pref.getInt(TOTAL_PAGE, 0)
        set(value) = pref.edit().putInt(TOTAL_PAGE, value).apply()

    override fun getPreviousBook(bookName: String): Int {
        return pref.getInt(bookName,0)
    }


    override fun setPreviousBook(bookName: String, value: Int) {
        pref.edit().putInt(bookName,value).apply()
    }
}