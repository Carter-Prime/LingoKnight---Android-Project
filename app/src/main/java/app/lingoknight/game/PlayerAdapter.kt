package app.lingoknight.game

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.lingoknight.R
import app.lingoknight.database.Player

import kotlinx.android.synthetic.main.player_list_item_layout.view.*


//
class PlayerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Player>() {

        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }

    }
    private val differPlayer = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.player_list_item_layout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlayerViewHolder -> {
                holder.bind(differPlayer.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differPlayer.currentList.size
    }

    fun submitList(list: LiveData<List<Player>>) {
        differPlayer.submitList(list.value)
    }

    class PlayerViewHolder constructor(itemView: View, private val interaction: Interaction?)
        : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Player) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
                Log.d("testing", "1. inside bind of playeradapter ${item.name}")
            }
            itemView.scoreNumber.text = item.playerScore.toString()
            itemView.playerName_itemView.text = item.name
            itemView.playerImage_itemView.setImageResource(when (item.pictureId) {
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
        fun onItemSelected(position: Int, item: Player){

        }
    }
}
