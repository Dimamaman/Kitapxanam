package uz.gita.dima.kitapxanam.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.databinding.ItemBookBinding
import uz.gita.dima.kitapxanam.databinding.SavedItemBinding
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


    inner class FavoriteViewHolder(private val binding: SavedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookData: BookData) {
            binding.apply {
                txtTitle.text = bookData.name
                txtYear.text = bookData.year
                txtKlass.text = bookData.klass
                Glide.with(itemView.context).load(bookData.imageUrl).into(bookImage)
            }

            binding.root.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.item_animation)

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
            SavedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}