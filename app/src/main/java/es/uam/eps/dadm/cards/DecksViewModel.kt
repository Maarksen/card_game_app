package es.uam.eps.dadm.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import es.uam.eps.dadm.cards.database.CardDatabase

class DecksViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    val decks: LiveData<List<DeckWithCards>> = CardDatabase.getInstance(context).cardDao.getDecksWithCards()
}