package uz.gita.dima.kitapxanam.data.local.sharedPref

interface SharedPref {
    var bookName: String?

    var klass: String?

    var savedPage: Int

    var totalPage: Int

    fun getPreviousBook(bookName: String): Int

    fun setPreviousBook(bookName: String, value: Int)

}