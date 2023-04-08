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
import es.uam.eps.dadm.cards.databinding.FragmentCardEditBinding

class CardEditFragment : Fragment() {
    lateinit var card: Card
    lateinit var binding: FragmentCardEditBinding
    lateinit var question: String
    lateinit var answer: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false
        )

        val args = CardEditFragmentArgs.fromBundle(requireArguments())
        card = CardsApplication.getCard(args.cardId) ?: throw Exception("Wrong id")
        binding.card = card
        question = card.question
        answer = card.answer

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val questionTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                question = s.toString()
            }
        }

        val answerTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                answer = s.toString()
            }
        }

        binding.editTextAnswer.addTextChangedListener(answerTextWatcher)
        binding.editTextQuestion.addTextChangedListener(questionTextWatcher)

        binding.acceptButton.setOnClickListener {
            card.question = question
            card.answer = answer
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment())
        }
        binding.cancelButton.setOnClickListener {
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment())
        }
    }
}