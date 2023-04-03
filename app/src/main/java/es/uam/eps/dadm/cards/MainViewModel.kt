package es.uam.eps.dadm.cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*

class StudyViewModel: ViewModel() {
    var card: Card? = null
    var nDueCards = MutableLiveData<Int>()
    private var cards: MutableList<Card> = CardsApplication.cards

    init {
         card = random_card()
        nDueCards.value = dueCards().size
    }

    private fun dueCards() = cards.filter { card -> card.is_due(LocalDateTime.now()) }

    private fun random_card() = if (dueCards().any()) dueCards().random() else null

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())
        card = random_card()
        nDueCards.value = nDueCards.value?.minus(1)
    }
}
