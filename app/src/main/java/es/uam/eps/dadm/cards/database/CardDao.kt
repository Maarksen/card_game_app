package es.uam.eps.dadm.cards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import es.uam.eps.dadm.cards.Card
import es.uam.eps.dadm.cards.Deck
import es.uam.eps.dadm.cards.DeckWithCards

@Dao
interface CardDao {
    @Transaction
    @Query("SELECT * FROM decks_table")
    fun getDecksWithCards(): LiveData<List<DeckWithCards>>

    @Transaction
    @Query("SELECT * FROM decks_table WHERE deck_id = :deck_id")
    fun getDeckWithCards(deck_id: Long): LiveData<List<DeckWithCards>>

    @Query("SELECT * FROM cards_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id: String): LiveData<Card?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Insert
    fun addCard(card: Card)

    @Update
    fun update(card: Card)
}