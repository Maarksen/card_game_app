package es.uam.eps.dadm.cards

import android.app.Application
import es.uam.eps.dadm.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.Executors

class CardsApplication: Application() {
    private val executor = Executors.newSingleThreadExecutor()
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
        val cardDatabase = CardDatabase.getInstance(applicationContext)
        executor.execute {
            cardDatabase.cardDao.addDeck(Deck("1", "English"))
            cardDatabase.cardDao.addDeck(Deck("2", "Frech"))
            cardDatabase.cardDao.addCard(
                Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(),"To wake up", "Despertarse", deck_id = "1")
            )
            cardDatabase.cardDao.addCard(
                Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(),"To rule out", "Descartar", deck_id = "1")
            )
            cardDatabase.cardDao.addCard(
                Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(),"To turn down", "Rechazar",deck_id = "1")
            )
            cardDatabase.cardDao.addCard(
                Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(),"La voiture", "El coche", deck_id = "2")
            )
            cardDatabase.cardDao.addCard(
                Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(),"J'ai faim", "Tengo hambre", deck_id = "2")
            )
        }
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
            return decks.find { it.deck_id == deckId}
        }

        fun addCard(card : Card){
            cards.add(card)
        }

        fun addDeck(deck : Deck){
            decks.add(deck)
        }
    }
}