package es.uam.eps.dadm.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentDeckEditBinding

class DeckEditFragment : Fragment() {
    val decks = CardsApplication.decks
    lateinit var deck: Deck
    lateinit var binding: FragmentDeckEditBinding
    lateinit var name: String
    lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_deck_edit,
            container,
            false
        )

        val args = DeckEditFragmentArgs.fromBundle(requireArguments())
        deck = CardsApplication.getDeck(args.deckId) ?: throw Exception("Wrong id")
        binding.deck = deck
        name = deck.name
        id = deck.deck_id

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val questionTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
            }
        }

        val answerTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                id = s.toString()
            }
        }

        binding.editTextAnswer.addTextChangedListener(answerTextWatcher)
        binding.editTextQuestion.addTextChangedListener(questionTextWatcher)

        binding.acceptButton.setOnClickListener {
            deck.name = name
            deck.deck_id = id
            it.findNavController()
                .navigate(DeckEditFragmentDirections.actionDeckEditFragmentToDecksFragment())
        }
        binding.cancelButton.setOnClickListener {
            it.findNavController()
                .navigate(DeckEditFragmentDirections.actionDeckEditFragmentToDecksFragment())
        }

        binding.deleteButton.setOnClickListener {
            decks.remove(deck)
            it.findNavController()
                .navigate(DeckEditFragmentDirections.actionDeckEditFragmentToDecksFragment())
        }
    }
}