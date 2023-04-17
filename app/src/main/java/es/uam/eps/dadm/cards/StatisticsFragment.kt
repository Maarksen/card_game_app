package es.uam.eps.dadm.cards

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import es.uam.eps.dadm.cards.databinding.FragmentStatisticsBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StatisticsFragment : Fragment() {
    val now = LocalDateTime.now()
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentStatisticsBinding>(
            inflater,
            R.layout.fragment_statistics,
            container,
            false
        )

        binding.barChart.invalidate()

        val entries = mutableListOf<BarEntry>()

        for (day in 0 until 7) {
            var num_cards = 0
            val time = now.plusDays(day.toLong()).format(formatter)
            CardsApplication.cards.forEach {
                if (it.next_practice == now.plusDays(day.toLong()).format(formatter)) {
                    num_cards++
                }
            }
            entries.add(BarEntry((day+1).toFloat(), num_cards.toFloat()))
        }

        val dataSet = BarDataSet(entries, "Cards to answer")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 12f

        val dataSets = mutableListOf<IBarDataSet>()
        dataSets.add(dataSet)

        val data = BarData(dataSets)
        data.setValueFormatter(object: ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })

        val chart = binding.barChart

        chart.data = data
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        chart.axisRight.isEnabled = false

        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.labelCount = 7
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)
        xAxis.valueFormatter = object: ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                return daysOfWeek[(value - 1).toInt()]
            }
        }

        val leftAxis = chart.axisLeft
        leftAxis.axisMinimum = 0f
        leftAxis.granularity = 1f

        chart.animateY(1000)
        chart.invalidate()

        binding.generateButton.setOnClickListener {
            for(i in 1..5){
                val card = Card(UUID.randomUUID().toString(), LocalDateTime.now().toString(), "TEST $i", "test $i")
                card.easiness = (0..5).random().toDouble()
                card.repetitions = (1..100).random()
                card.interval = (1..10).random().toLong()
                CardsApplication.cards.add(card)
            }
        }

        return binding.root
    }

    companion object {
    }
}