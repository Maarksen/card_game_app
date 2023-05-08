package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import es.uam.eps.dadm.cards.databinding.FragmentStudyBinding
import timber.log.Timber

class StudyFragment : Fragment() {

        lateinit var binding : FragmentStudyBinding
        private val viewModel: StudyViewModel by lazy {
            ViewModelProvider(this).get(StudyViewModel::class.java)
        }
        //val arg = CardListFragmentArgs.fromBundle(requireArguments())

        private var listener = View.OnClickListener { v ->
            val quality: Int = when (v?.id) {
                binding.easyButton.id -> 5
                binding.doubtButton.id -> 3
                binding.diffButton.id -> 0
                else -> -1
            }
            lateinit var deck_id : String
            if(viewModel.card != null)
                deck_id = viewModel.run {
                    card?.deck_id!!
                }
            binding.viewModel?.update(quality)

            if (viewModel.card == null) {
                Toast.makeText(activity, "No more cards to review", Toast.LENGTH_SHORT).show()
                view?.findNavController()
                    ?.navigate(StudyFragmentDirections.actionStudyFragmentToCardListFragment(deck_id))
            }
            binding.invalidateAll()
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
            binding.card = viewModel.card
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
            binding.answerButton.setOnClickListener {
                Timber.i("${viewModel.card}")
                viewModel.card?.answered = true
                binding.invalidateAll()
            }

            viewModel.dueCard.observe(viewLifecycleOwner) {
                viewModel.card = it
                binding.invalidateAll()
            }

            binding.easyButton.setOnClickListener(listener)
            binding.doubtButton.setOnClickListener(listener)
            binding.diffButton.setOnClickListener(listener)

            Timber.i("onCreate called")
            Timber.i("---------- yes this is it :${binding.card?.question}")

        return binding.root
        }

        override fun onStart() {
            super.onStart()
            if(viewModel.get_board_settings()){
                binding.boardView?.isGone = false
            }
            else{
                binding.boardView?.isGone = true
            }
            Timber.i("onStart called")
        }

        override fun onResume() {
            super.onResume()
            Timber.i("onResume called")
            binding.invalidateAll()
        }

        override fun onPause() {
            super.onPause()
            Timber.i("onPause called")
        }

        override fun onStop() {
            super.onStop()
            Timber.i("onStop called")
        }

        override fun onDestroy() {
            super.onDestroy()
            Timber.i("onDestroy called")
        }
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            Timber.i("onSaveInstanceState called")
        }

        /*override fun onRestoreInstanceState(savedInstanceState: Bundle) {
            super.onRestoreInstanceState(savedInstanceState)
        }*/


    }