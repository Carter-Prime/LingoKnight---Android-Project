package app.lingoknight.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.lingoknight.R
import app.lingoknight.database.Word


class WordAdapter: RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    var data =  listOf<Word?>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val wordText: TextView = itemView.findViewById(R.id.word_text_item_view)
        private val qualityImage: ImageView = itemView.findViewById(R.id.word_image_item_view)

        fun bind(item: Word?) {
            val res = itemView.context.resources
            wordText.text = item?.text
            qualityImage.setImageResource(when (item?.text) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.word_list_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setWords(words: List<Word?>){
        this.data = words
        notifyDataSetChanged()
    }


}