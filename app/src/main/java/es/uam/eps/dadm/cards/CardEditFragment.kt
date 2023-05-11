package es.uam.eps.dadm.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentCardEditBinding
import java.util.concurrent.Executors

class CardEditFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()

    val cards = CardsApplication.cards
    lateinit var card: Card
    lateinit var binding: FragmentCardEditBinding
    lateinit var question: String
    lateinit var answer: String

    private var reference = FirebaseDatabase
        .getInstance()
        .getReference("cards")

    private val viewModel by lazy {
        ViewModelProvider(this).get(CardEditFirebaseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false
        )

        val args = CardEditFragmentArgs.fromBundle(requireArguments())
        //viewModel.loadCardId(args.cardId)
        viewModel.cards.observe(viewLifecycleOwner) {
            card = it.find { it.id == args.cardId }!!
            binding.card = card
            question = card.question
            answer = card.answer
        }

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
            //executor.execute { context?.let { it1 -> CardDatabase.getInstance(it1).cardDao.update(card) } }
            reference.child(card.id).setValue(card)
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(card.deck_id))
        }
        binding.cancelButton.setOnClickListener {
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(card.deck_id))
        }

        binding.deleteButton.setOnClickListener {
            cards.remove(card)
            it.findNavController()
                .navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(card.deck_id))
        }
    }
}