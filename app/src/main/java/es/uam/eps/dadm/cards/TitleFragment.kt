package es.uam.eps.dadm.cards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import es.uam.eps.dadm.cards.databinding.FragmentTitleBinding

class TitleFragment: Fragment() {

    interface OnTitleFragmentInteractionListener {
        fun onStudy()
    }
    var listener: OnTitleFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false)

        binding.cardsTitleTextView.setOnClickListener {
            var fragment = StudyFragment()

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }

        return binding.root
    }
}