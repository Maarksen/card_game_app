package es.uam.eps.dadm.cards

import java.io.File
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Deck(var id : String = UUID.randomUUID().toString(), var name : String){
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    var cards = mutableListOf<Card>()

    fun add_card(){
        println("Adding card to $name deck.")
        println("Type the type of the question.[1/2]")

        val q_type = readln().toIntOrNull()?: "[ERROR] wrong input"
        if(q_type != 1 && q_type != 2){
            println("[ERROR] wrong input")
            return
        }
        println("Type the question.")
        val question = readln()
        println("Type the answer.")
        val answer = readln()

        when (q_type) {
            1 -> {
                cards.add(Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), question, answer))
                println("Card added successfully.")
            }
            2 -> {
                println("Card added successfully.")
            }
            else -> println("[ERROR] Wrong Input")
        }
    }

    fun deck_add_card(card : Card){
        cards.add(card)
    }

    fun remove_card() {
        println("Type the question.")
        val delete = readln()

        if (cards.removeIf { it.question.trim() == delete })
            println("Card removed successfully.")
        else
            println("[ERROR] Card was not found.")
    }


    fun simulate(num_days : Long){
        val now = LocalDateTime.now()

        println("Simulation of the deck $name")
        println("Date: ${now.format(formatter)}")

        cards.forEach{it.show_card(now)}

        for (day in 1..num_days) {
            val time = now.plusDays(day).format(formatter)
            println("Date: $time")
            cards.forEach{if(it.next_practice == now.plusDays(day).format(formatter)) it.show_card(now.plusDays(day))}
        }
        println("Do you want to see the statistics? [yes/no]")
        if(readln().trim().uppercase() == "YES"){
            statistics()
        }
        else if(readln().trim().uppercase() != "NO")
            println("[ERROR] wrong input")
    }

    private fun statistics(){
        var sum_calls = 0; var sum_diff = 0; var sum_doubt = 0; ; var sum_easy = 0
        val now = LocalDateTime.now()
        var sum_eas = 0.0

        cards.forEach{
            sum_eas += it.average_easiness
            sum_calls += it.num_calls
            sum_diff += it.num_diff
            sum_doubt += it.num_doubt
            sum_easy += it.num_easy
        }
        println("------------------STATISTICS------------------")
        println("Number of DIFFICULT questions: $sum_diff")
        println("Number of DOUBT questions: $sum_doubt")
        println("Number of EASY questions: $sum_easy")
        println("The average easiness was ${"%.2f".format(sum_eas/sum_calls)}.")
        println("The success rate was ${"%.2f".format((sum_easy.toDouble()/sum_calls.toDouble()) * 100.0)}%.")

        for (day in 1..30) {
            var num_cards = 0
            val time = now.plusDays(day.toLong()).format(formatter)
            cards.forEach{if(it.next_practice == now.plusDays(day.toLong()).format(formatter)) num_cards++}
            if(num_cards != 0)
                println("On $time there are $num_cards questions to answer.")
        }
        println("----------------------------------------------")
    }

    fun writeCards(){
        println("Type the name of the file.")
        val file = File(readln())
        file.createNewFile()

        file.printWriter().use { out ->
            cards.forEach {
                out.println(it.toString())
            }
        }
    }

    fun readCards(){
        println("Type the name of the file.")
        val file = readln()

        val inputStream: InputStream = File(file).inputStream()
        inputStream.bufferedReader().forEachLine {
            if (it.isNotBlank()) {
                val chunks = it.split("|")
                if (chunks[0].trim() == "card")
                    cards.add(Card.fromString(it))
            }
        }
    }
}