package uz.gita.dima.kitapxanam.presenter.adapters

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.databinding.SearchListItemProductsBinding
import uz.gita.dima.kitapxanam.util.inflate

class SearchAdapter :
    ListAdapter<BookData, SearchAdapter.ViewHolder>(itemProductsCallback) {

    private var itemBasketClickListener: ((BookData) -> Unit)? = null

    fun setItemClickListener(block: (BookData) -> Unit) {
        itemBasketClickListener = block
    }


    inner class ViewHolder(val binding: SearchListItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemBasketClickListener?.invoke(getItem(adapterPosition))
            }
        }

        fun onBind() {
            binding.apply {
                val data = getItem(adapterPosition)
                Glide.with(binding.klassName.context)
                    .load(data.imageUrl)
                    .placeholder(R.drawable.open_book)
                    .into(binding.klassName)
                tvBookName.text = data.name
                tvBookPage.text = data.klass
                constraint.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.item_animation)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        SearchListItemProductsBinding.bind(parent.inflate(R.layout.search_list_item_products))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

}

private var itemProductsCallback = object : DiffUtil.ItemCallback<BookData>() {
    override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean =
        oldItem.klass == newItem.klass && oldItem.name == newItem.name
}