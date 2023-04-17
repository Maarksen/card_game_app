package es.uam.eps.dadm.cards

import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.max
import kotlin.math.roundToLong

open class Card(var id: String = UUID.randomUUID().toString(), var date: String = LocalDateTime.now().toString(),
                open var question: String, open var answer: String){

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    var quality : Int = 0
    var easiness : Double = 2.5
    var repetitions : Int = 0
    var interval : Long = 1L
    var next_practice : String = LocalDateTime.now().format(formatter).toString()

    //statistics
    var average_easiness : Double = 0.0
    var num_calls : Int = 0
    var num_diff : Int = 0
    var num_doubt : Int = 0
    var num_easy : Int = 0

    //actual app
    var answered : Boolean = false
    var deck_id : String = ""

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

    fun update_deck_id(id : String){
        this.deck_id = id
    }

    fun is_due(date: LocalDateTime) = date.format(formatter) >= next_practice

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