package uz.gita.dima.kitapxanam.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.dima.kitapxanam.R
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.databinding.ItemBookBinding
import java.text.SimpleDateFormat
import java.util.*

class BookAdapter : ListAdapter<BookData, BookAdapter.ViewHolder>(MyDiffUtil) {

    object MyDiffUtil : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }
    }

    var clickListener: ((BookData) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickListener?.invoke(getItem(adapterPosition))
            }
        }

        fun bind(data: BookData) = with(binding) {
            txtTitle.text = data.name
            txtYear.text = data.year
//            txtDate.text = data.date
            if (data.klass >= 5.toString()) {
                txtKlass.text = data.klass
            } else {
                txtKlass.text = data.klass
            }

            if (checkIsNew(data.date)) {
                imgNew.visibility = View.VISIBLE
            } else {
                imgNew.visibility = View.GONE
            }

            Glide
                .with(root.context)
                .load(data.image_url)
                .centerCrop()
                .into(bookImage)
        }

    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun checkIsNew(date: String): Boolean {
        val curentDay = Calendar.getInstance().time.toString("dd").toInt()
        val curentMonth = Calendar.getInstance().time.toString("MM").toInt()

        val day = date.subSequence(0, 2).toString().toInt()
        val month = date.subSequence(3, 5).toString().toInt()

        if ((Math.abs(curentDay - day)) <= 5 && (month - curentMonth) == 0) {
            return true
        }
        return false

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animatsiya)
        getItem(position)?.let { holder.bind(it) }
    }
}
