package es.uam.eps.dadm.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentDecksBinding

class DecksFragment : Fragment() {
    private lateinit var adapter: DeckAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDecksBinding>(
            inflater,
            R.layout.fragment_decks,
            container,
            false
        )
        adapter = DeckAdapter()
        adapter.data = CardsApplication.decks
        binding.cardListRecyclerView.adapter = adapter

        binding.newDeckFab.setOnClickListener {
            val deck = Deck("", "")
            CardsApplication.addDeck(deck)
            it.findNavController().navigate(DecksFragmentDirections.actionDecksFragmentToDeckEditFragment(deck.id))
        }

        return binding.root
    }
}