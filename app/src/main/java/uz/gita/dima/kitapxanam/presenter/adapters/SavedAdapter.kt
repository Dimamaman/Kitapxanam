package uz.gita.dima.kitapxanam.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.databinding.ItemBookBinding
import javax.inject.Inject

class SavedAdapter @Inject constructor() :
    ListAdapter<BookData, SavedAdapter.FavoriteViewHolder>(DIFF_CALL_BACK) {

    private var clickListener: ((BookData) -> Unit)? = null
    private var deleteClickListener: ((BookData) -> Unit)? = null

    fun setClickListener(l: (BookData) -> Unit) {
        clickListener = l
    }

    fun setDeleteClickListener(l: (BookData) -> Unit) {
        deleteClickListener = l
    }


    inner class FavoriteViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookData: BookData) {
            binding.apply {
                txtTitle.text = bookData.name
                Glide.with(itemView.context).load(bookData.imageUrl).into(bookImage)
            }
            binding.root.setOnClickListener {
                clickListener?.invoke(bookData)
            }

            binding.root.setOnLongClickListener {
                deleteClickListener?.invoke(bookData)
                true
            }
        }
    }

    companion object {
        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<BookData>() {
            override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}