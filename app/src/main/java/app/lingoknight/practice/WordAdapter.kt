package app.lingoknight.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.lingoknight.R
import app.lingoknight.database.Word
import kotlinx.android.synthetic.main.word_list_item_layout.view.*


//
class WordAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Word?>() {

        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.word_list_item_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WordViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: LiveData<List<Word?>>) {
        differ.submitList(list.value)
    }

    class WordViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Word?) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.word_text_item_view.text = item?.text
            itemView.word_image_item_view.setImageResource(when (item?.text) {
                "king" -> R.drawable.king
                "knight" -> R.drawable.knight
                "princess" -> R.drawable.princess
                "witch" -> R.drawable.witch
                "fox" -> R.drawable.fox
                "purple" -> R.drawable.purple_monster
                "blue" -> R.drawable.blue_bird
                "dragon" -> R.drawable.dragon
                "monster" -> R.drawable.green_monster
                "green" -> R.drawable.green_troll
                "tree" -> R.drawable.tree
                "yellow" -> R.drawable.yellow_monster
                "horse" -> R.drawable.horse
                "goat" -> R.drawable.goat
                else -> R.drawable.oops_comic
            })
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Word?)
    }
}
