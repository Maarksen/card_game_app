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
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()
    }
}