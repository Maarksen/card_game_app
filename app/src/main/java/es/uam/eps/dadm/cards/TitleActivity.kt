package es.uam.eps.dadm.cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.cards.databinding.ActivityStudyBinding
import es.uam.eps.dadm.cards.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)

        val fragment = TitleFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}