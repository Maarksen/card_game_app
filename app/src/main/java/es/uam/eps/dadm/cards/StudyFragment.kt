package es.uam.eps.dadm.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import es.uam.eps.dadm.cards.databinding.FragmentStudyBinding
import timber.log.Timber
import java.time.LocalDateTime

private const val ANSWERED_KEY = "es.uam.eps.dadm.cards:answered"
class StudyFragment : Fragment() {

        private var date: LocalDateTime = LocalDateTime.now()
        lateinit var binding : FragmentStudyBinding
        private val viewModel: StudyViewModel by lazy {
            ViewModelProvider(this).get(StudyViewModel::class.java)
        }

        private var listener = View.OnClickListener { v ->
            val quality: Int = when (v?.id) {
                binding.easyButton.id -> 5
                binding.doubtButton.id -> 3
                binding.diffButton.id -> 0
                else -> -1
            }

            binding.viewModel?.update(quality)

            if (viewModel.card == null)
                Toast.makeText(activity, "No more cards to review", Toast.LENGTH_SHORT).show()

            binding.invalidateAll()
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
            binding.card = viewModel.card
            binding.viewModel = viewModel
            binding.answerButton.setOnClickListener {
                Timber.i("${viewModel.card}")
                viewModel.card?.answered = true
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