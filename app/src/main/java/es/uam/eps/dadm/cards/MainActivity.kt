package es.uam.eps.dadm.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.cards.databinding.ActivityMainBinding
import timber.log.Timber

//card class imports
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.max
import kotlin.math.roundToLong

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
}

open class Card(var id: String = UUID.randomUUID().toString(), var date: String = LocalDateTime.now().toString(),
                open var question: String, open var answer: String){

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    var quality : Int = 0
    var easiness : Double = 2.5
    var repetitions : Int = 0
    var interval : Long = 1L
    var next_practice : String = date

    //statistics
    var average_easiness : Double = 0.0
    var num_calls : Int = 0
    var num_diff : Int = 0
    var num_doubt : Int = 0
    var num_easy : Int = 0

    //actual app
    var answered : Boolean = false

    open fun show_card(date: LocalDateTime){
        println("${question.trim()} [ENTER]")
        val input = readln()

        if(input == ""){
            println("${answer.trim()} [TYPE : 0 -> DIFFICULT | 3 -> DOUBT | 5 -> EASY]")
            val difficulty = readln().toInt()
            quality = difficulty
        }

        update(date)
        details()
    }

    open fun simulate(num_days : Long){
        println("Simulation of the card $question")
        val now = LocalDateTime.now()
        show_card(now)
        var jump = interval
        for(day in 1 .. num_days){
            val time = now.plusDays(day).format(formatter)
            if(day == jump){
                show_card(now.plusDays(day))
                jump += interval
            }
            else
                println("Date: $time")
        }
    }

    private fun easiness() : Double{
        return max(1.3, easiness + 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02))
    }

    private fun repetition() : Int{
        if(quality < 3)
            return 0
        else
            return (repetitions + 1)
    }

    private fun interval() : Long {
        if(repetitions <= 1)
            return 1
        else if(repetitions == 2)
            return 6
        else
            return (interval.toDouble() * easiness).roundToLong()
    }

    fun update(curr_date : LocalDateTime){
        easiness = easiness()
        average_easiness += easiness
        num_calls ++
        when(quality){
            0 -> num_diff++
            3 -> num_doubt++
            5 -> num_easy++
        }
        repetitions = repetition()
        interval = interval()
        next_practice = curr_date.plusDays(interval).format(formatter).toString()
    }

    fun update_card(quality: Int) {
        this.quality = quality
        update(LocalDateTime.now())
    }


    fun details(){
        println("eas = ${"%.2f".format(easiness)} rep = $repetitions int = $interval next = $next_practice")
    }

    override fun toString(): String {
        return "card | $question | $answer | $date | $id | $easiness | $repetitions | $interval | $next_practice"
    }

    companion object {
        fun fromString(line: String): Card {
            val chunks = line.split(" | ")
            chunks.forEach { it.trim() }

            val card = Card(chunks[4], chunks[3], chunks[1], chunks[2])
            card.easiness = chunks[5].toDouble()
            card.repetitions = chunks[6].toInt()
            card.interval = chunks[7].toLong()
            card.next_practice = chunks[8]

            return card
        }
    }
}