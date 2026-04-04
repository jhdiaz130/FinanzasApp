package com.example.finanzasapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class BudgetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        val pieChart = findViewById<PieChart>(R.id.pieChart)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(20f, "Comida"))
        entries.add(PieEntry(15f, "Transporte"))
        entries.add(PieEntry(10f, "Entretenimiento"))
        entries.add(PieEntry(25f, "Arriendo"))
        entries.add(PieEntry(15f, "Servicios"))
        entries.add(PieEntry(15f, "Otros"))

        val dataSet = PieDataSet(entries, "")

        dataSet.colors = listOf(
            Color.YELLOW,
            Color.parseColor("#FF9800"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#CE93D8"),
            Color.BLUE,
            Color.GREEN
        )

        dataSet.valueTextColor = Color.BLACK

        // ✅ AQUÍ ESTÁ EL ARREGLO
        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueTextColor(Color.BLACK)

        pieChart.data = data

        pieChart.setEntryLabelColor(Color.BLACK)

        pieChart.description.isEnabled = false
        pieChart.centerText = "Gastos"
        pieChart.setCenterTextSize(16f)

        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}