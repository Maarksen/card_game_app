package es.uam.eps.dadm.cards

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    private var holderCounter = 0
    var data = listOf<Card>()

    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            holderCounter++
            Timber.i("CardHolder number $holderCounter created")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(TextView(parent.context))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: CardHolder,
        position: Int
    ) {
        val item = data[position]
        (holder.itemView as TextView).text = "${item.question} \n ${item.answer}"
    }


}