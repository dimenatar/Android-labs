package com.example.lab3

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val nameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val typeTextView: TextView = itemView.findViewById(R.id.item_type)
        private val authorTextView: TextView = itemView.findViewById(R.id.item_author)
        private val priceTextView: TextView = itemView.findViewById(R.id.item_price)
        private val multiplayerTextView: TextView = itemView.findViewById(R.id.item_multiplayer)
        private val telephoneTextView: TextView = itemView.findViewById(R.id.item_telephone)
        private val emailTextView: TextView = itemView.findViewById(R.id.item_email)
        private val linkTextView: TextView = itemView.findViewById(R.id.item_link)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val game = games[position]
                val context = view.context

                // Создайте Intent для открытия активности с этим элементом
                val intent = Intent(context, DetailedGameActivity::class.java)
                intent.putExtra("Game", game) // Передайте объект Game через Intent

                context.startActivity(intent)
            }
        }

        fun bind(game: Game) {
            nameTextView.text = game.name
            typeTextView.text = game.type
            authorTextView.text = game.author
            priceTextView.text = game.price.toString()
            multiplayerTextView.text = if (game.isMultiplayer) "Да" else "Нет"
            telephoneTextView.text = game.telephone
            emailTextView.text = game.email
            linkTextView.text = game.link
        }
    }
}