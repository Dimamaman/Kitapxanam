package uz.gita.dima.kitapxanam.presenter.sreens.readbook.viewmodel

import uz.gita.dima.kitapxanam.data.model.BookData

interface ReadViewModel {
    fun saveBookAsRead(data: BookData, currentPage: Int)
}
