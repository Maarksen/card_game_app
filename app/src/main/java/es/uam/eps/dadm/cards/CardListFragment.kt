package es.uam.eps.dadm.cards

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentCardListBinding
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.Executors

class CardListFragment: Fragment() {
    private lateinit var adapter: CardAdapter
    private val executor = Executors.newSingleThreadExecutor()

    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListFirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private var reference = FirebaseDatabase
        .getInstance()
        .getReference("cards")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        SettingsActivity.setLoggedIn(requireContext(), true)
        val binding = DataBindingUtil.inflate<FragmentCardListBinding>(
            inflater,
            R.layout.fragment_card_list,
            container,
            false
        )
        val arg = CardListFragmentArgs.fromBundle(requireArguments())

        adapter = CardAdapter()
        adapter.data = emptyList()
        binding.cardListRecyclerView.adapter = adapter

        binding.reviewButton.setOnClickListener { view ->
            if (CardsApplication.numberOfDueCards() > 0)
                view.findNavController()
                    .navigate(CardListFragmentDirections.actionCardListFragmentToStudyFragment())
            else
                Toast.makeText(requireActivity(), R.string.no_more_cards_toast_message, Toast.LENGTH_LONG).show()
        }

        binding.newCardFab.setOnClickListener {
            val card = Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "", "")
            card.update_deck_id(arg.deckId)
           //CardsApplication.addCard(card)
            //executor.execute { context?.let { it1 -> CardDatabase.getInstance(it1).cardDao.addCard(card) } }
            Timber.i("ano dostal som sa sem")
            reference.child(card.id).setValue(card)
            it.findNavController().navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id))
        }

        cardListViewModel.cards.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
            }
        }
        return true
    }
}