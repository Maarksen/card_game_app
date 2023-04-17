package es.uam.eps.dadm.cards

import android.app.Application
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*

class CardsApplication: Application() {
    init {
        cards.add(Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "To speak", "Hablar"))
        cards.add(Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "To sleep", "Dormir"))
        cards.add(Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "To wake up", "Despertarse"))
        cards.forEach { it.update_deck_id("yes") }

        decks.add(Deck("yes", "no"))
        val deck = Deck("deck_id", "deck_name")
        deck.deck_add_card(Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "1", "2"))
        deck.deck_add_card(Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "3", "4"))
        decks.add(deck)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var date: LocalDateTime = LocalDateTime.now()
        var cards: MutableList<Card> = mutableListOf<Card>()
        var decks: MutableList<Deck> = mutableListOf<Deck>()

        fun numberOfDueCards(): Int {
            var num = 0
            cards.forEach{
                if(it.is_due(date)){
                    num++
                }
            }
            return num
        }
        fun getCard(cardId : String): Card?{
            return cards.find { it.id == cardId}
        }

        fun getDeck(deckId : String): Deck?{
            return decks.find { it.id == deckId}
        }

        fun addCard(card : Card){
            cards.add(card)
        }

        fun addDeck(deck : Deck){
            decks.add(deck)
        }
    }
}