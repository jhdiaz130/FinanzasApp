package com.example.finanzasapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.NumberFormat

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvGastos: TextView
    private lateinit var tvIngresos: TextView
    private lateinit var tvBalance: TextView
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataManager.load(this)
        setContentView(R.layout.activity_dashboard)

        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        val btnGrafica = findViewById<Button>(R.id.btnVerGrafica)

        tvGastos = findViewById(R.id.tvGastosMes)
        tvIngresos = findViewById(R.id.tvIngresosMes)
        tvBalance = findViewById(R.id.tvBalance)
        container = findViewById(R.id.containerMovimientos)

        // Agregar transacción
        fab.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }

        // Ver gráfica
        btnGrafica.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarUI()
    }

    private fun actualizarUI() {

        var totalGastos = 0
        var totalIngresos = 0

        container.removeAllViews()

        for (i in DataManager.transactions.indices) {

            val t = DataManager.transactions[i]

            if (t.tipo == "gasto") {
                totalGastos += t.monto
            } else {
                totalIngresos += t.monto
            }

            val textView = TextView(this)

            val signo = if (t.tipo == "ingreso") "+" else "-"
            val icono = obtenerIcono(t.categoria)

            // TEXTO ESTILO APP BANCARIA
            textView.text = "$icono $signo ${formatearDinero(t.monto)}\n${t.categoria} • ${t.fecha}"
            textView.textSize = 16f
            textView.setPadding(0, 16, 0, 16)

            // COLOR
            if (t.tipo == "ingreso") {
                textView.setTextColor(Color.parseColor("#4CAF50"))
            } else {
                textView.setTextColor(Color.parseColor("#F44336"))
            }

            // EDITAR
            textView.setOnClickListener {
                val intent = Intent(this, AddTransactionActivity::class.java)
                intent.putExtra("index", i)
                startActivity(intent)
            }

            // ELIMINAR
            textView.setOnLongClickListener {
                DataManager.transactions.removeAt(i)
                DataManager.save(this)
                actualizarUI()
                true
            }

            container.addView(textView)
        }

        val balance = totalIngresos - totalGastos

        //RESUMEN
        tvGastos.text = formatearDinero(totalGastos)
        tvIngresos.text = formatearDinero(totalIngresos)
        tvBalance.text = formatearDinero(balance)
    }

    // FORMATO DINERO
    private fun formatearDinero(valor: Int): String {
        val formato = NumberFormat.getInstance()
        return "$" + formato.format(valor)
    }

    //ICONOS POR CATEGORÍA
    private fun obtenerIcono(categoria: String): String {
        return when (categoria.lowercase()) {
            "comida" -> "🍔"
            "transporte" -> "🚗"
            "arriendo" -> "🏠"
            "salud" -> "💊"
            "educacion" -> "📚"
            "entretenimiento" -> "🎮"
            "ropa" -> "👕"
            "ahorro" -> "💰"
            else -> "📌"
        }
    }
}