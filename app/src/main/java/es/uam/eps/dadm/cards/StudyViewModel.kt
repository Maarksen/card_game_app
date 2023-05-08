package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.*
import es.uam.eps.dadm.cards.database.CardDatabase
import java.lang.Exception
import java.lang.StrictMath.random
import java.time.LocalDateTime
import java.util.concurrent.Executors

class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()
    private val context = getApplication<Application>().applicationContext

    var card: Card? = null
    var cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()
    var nDueCards: LiveData<Int> = Transformations.map(cards) {
        it.filter { card -> card.is_due(LocalDateTime.now()) }.size
    }

    var dueCard: LiveData<Card?> = Transformations.map(cards) {
        it.filter { card -> card.is_due(LocalDateTime.now()) }.run {
            if (any()) random() else null
        }
    }

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())
        executor.execute {
            CardDatabase.getInstance(context).cardDao.update(card!!)
        }
    }

    fun get_board_settings():Boolean{
        return SettingsActivity.get_board_pref(context)
    }
}