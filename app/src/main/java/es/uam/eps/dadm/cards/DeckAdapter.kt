package es.uam.eps.dadm.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.cards.databinding.ListItemDeckBinding


class DeckAdapter() : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    var data = listOf<Deck>()
    lateinit var binding : ListItemDeckBinding

    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var local = binding
        fun bind(deck: Deck) {
            local.deck = deck
            itemView.setOnClickListener {
                it.findNavController()
                    .navigate(DecksFragmentDirections.actionDecksFragmentToCardListFragment(deck.id))
            }

            binding.editButton.setOnClickListener {
                it.findNavController()
                    .navigate(DecksFragmentDirections.actionDecksFragmentToDeckEditFragment(deck.id))
            }
        }
    }

    companion object{

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent, false)
        return DeckHolder(binding.root)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DeckHolder, position: Int) {
        holder.bind(data[position])
    }
}