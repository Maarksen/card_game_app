package es.uam.eps.dadm.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.cards.databinding.ActivityMainBinding
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity() {
    private var date: LocalDateTime = LocalDateTime.now()
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val name = resources.getString(R.string.app_name)
        Toast.makeText(this, name, Toast.LENGTH_LONG).show()

        val card = Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "Tree", "Arbol")
        binding.card = card

        binding.answerButton.setOnClickListener {
            card.answered = true
            //binding.invalidateAll() //this is a possibility with few changes in the xml file
            binding.answerTextView.visibility = View.VISIBLE
            binding.separatorView.visibility = View.VISIBLE
            binding.answerButton.visibility = View.INVISIBLE

            //quality buttons
            binding.easyButton.visibility = View.VISIBLE
            binding.doubtButton.visibility = View.VISIBLE
            binding.diffButton.visibility = View.VISIBLE
        }
        Timber.i("onCreate called")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
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


}
