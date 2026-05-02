package com.example.finanzasapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.SimpleDateFormat
import java.util.*

class BudgetActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var btnMes: Button
    private lateinit var btnSemana: Button
    private lateinit var btnRango: Button

    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataManager.load(this)
        setContentView(R.layout.activity_budget)

        pieChart = findViewById(R.id.pieChart)
        btnMes = findViewById(R.id.btnMes)
        btnSemana = findViewById(R.id.btnSemana)
        btnRango = findViewById(R.id.btnRango)

        // 🔥 Estado inicial
        activarBoton(btnMes)
        cargarGrafica(DataManager.transactions)

        btnMes.setOnClickListener {
            activarBoton(btnMes)
            cargarGrafica(filtrarMes())
        }

        btnSemana.setOnClickListener {
            activarBoton(btnSemana)
            cargarGrafica(filtrarSemana())
        }

        btnRango.setOnClickListener {
            activarBoton(btnRango)
            seleccionarRango()
        }
    }

    // BOTÓN ACTIVO
    private fun activarBoton(botonActivo: Button) {

        val activoColor = "#1565C0".toColorInt()
        val inactivoColor = "#E0E0E0".toColorInt()

        val lista = listOf(btnMes, btnSemana, btnRango)

        for (btn in lista) {
            if (btn == botonActivo) {
                btn.setBackgroundColor(activoColor)
                btn.setTextColor("#FFFFFF".toColorInt())
            } else {
                btn.setBackgroundColor(inactivoColor)
                btn.setTextColor("#000000".toColorInt())
            }
        }
    }

    private fun cargarGrafica(lista: List<Transaction>) {

        val mapa = HashMap<String, Float>()

        for (t in lista) {
            if (t.tipo == "gasto") {
                val actual = mapa[t.categoria] ?: 0f
                mapa[t.categoria] = actual + t.monto
            }
        }

        val entries = ArrayList<PieEntry>()

        for ((categoria, total) in mapa) {
            entries.add(PieEntry(total, categoria))
        }

        if (entries.isEmpty()) {
            entries.add(PieEntry(1f, "Sin datos"))
        }

        val dataSet = PieDataSet(entries, "Gastos")

        dataSet.colors = listOf(
            "#4CAF50".toColorInt(),
            "#2196F3".toColorInt(),
            "#FF9800".toColorInt(),
            "#F44336".toColorInt(),
            "#9C27B0".toColorInt()
        )

        val data = PieData(dataSet)
        data.setValueTextSize(14f)

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.centerText = "Gastos por categoría"
        pieChart.animateY(1000)
        pieChart.invalidate()
    }

    private fun filtrarMes(): List<Transaction> {
        val cal = Calendar.getInstance()
        val mes = cal.get(Calendar.MONTH)
        val anio = cal.get(Calendar.YEAR)

        return DataManager.transactions.filter {
            val fecha = sdf.parse(it.fecha) ?: return@filter false
            val c = Calendar.getInstance()
            c.time = fecha

            c.get(Calendar.MONTH) == mes &&
                    c.get(Calendar.YEAR) == anio
        }
    }

    private fun filtrarSemana(): List<Transaction> {
        val cal = Calendar.getInstance()
        val semana = cal.get(Calendar.WEEK_OF_YEAR)
        val anio = cal.get(Calendar.YEAR)

        return DataManager.transactions.filter {
            val fecha = sdf.parse(it.fecha) ?: return@filter false
            val c = Calendar.getInstance()
            c.time = fecha

            c.get(Calendar.WEEK_OF_YEAR) == semana &&
                    c.get(Calendar.YEAR) == anio
        }
    }

    private fun seleccionarRango() {

        val cal = Calendar.getInstance()

        DatePickerDialog(this, { _, y1, m1, d1 ->

            val inicio = Calendar.getInstance()
            inicio.set(y1, m1, d1)

            DatePickerDialog(this, { _, y2, m2, d2 ->

                val fin = Calendar.getInstance()
                fin.set(y2, m2, d2)

                val filtradas = DataManager.transactions.filter {
                    val fecha = sdf.parse(it.fecha) ?: return@filter false
                    val c = Calendar.getInstance()
                    c.time = fecha

                    !c.before(inicio) && !c.after(fin)
                }

                cargarGrafica(filtradas)

            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()

        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }
}