package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*

class CardListFragment: Fragment() {
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentCardListBinding>(
            inflater,
            R.layout.fragment_card_list,
            container,
            false
        )

        //deck = CardsApplication.getCard(args.deckId) ?: throw Exception("Wrong id")

        val arg = CardListFragmentArgs.fromBundle(requireArguments())

        adapter = CardAdapter()
        adapter.data = CardsApplication.cards.filter{it.deck_id == arg.deckId}
        binding.cardListRecyclerView.adapter = adapter

        binding.reviewButton.setOnClickListener { view ->
            if (CardsApplication.numberOfDueCards() > 0)
                view.findNavController()
                    .navigate(CardListFragmentDirections.actionCardListFragmentToStudyFragment())
                    //.navigate(R.id.action_cardListFragment_to_studyFragment)
            else
                Toast.makeText(requireActivity(), R.string.no_more_cards_toast_message, Toast.LENGTH_LONG).show()
        }

        binding.newCardFab.setOnClickListener {
            val card = Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "", "")
            card.update_deck_id(arg.deckId)
            CardsApplication.addCard(card)
            Timber.i("ano dostal som sa sem")
            it.findNavController().navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id))
        }

        return binding.root
    }
}