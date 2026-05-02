package com.example.finanzasapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvGastos: TextView
    private lateinit var tvBalance: TextView // 👈 NUEVO
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        tvGastos = findViewById(R.id.tvGastosMes)
        tvBalance = findViewById(R.id.tvBalance) // 👈 NUEVO
        container = findViewById(R.id.containerMovimientos)

        fab.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        var totalGastos = 0
        var totalIngresos = 0 // 👈 NUEVO

        container.removeAllViews()

        for (t in DataManager.transactions) {

            // 🔹 SUMAR
            if (t.tipo == "gasto") {
                totalGastos += t.monto
            } else {
                totalIngresos += t.monto
            }

            // 🔹 CREAR ITEM VISUAL
            val textView = TextView(this)

            val signo = if (t.tipo == "ingreso") "+" else "-"

            textView.text = "$signo $${t.monto}  ${t.categoria}"
            textView.textSize = 16f

            if (t.tipo == "ingreso") {
                textView.setTextColor(Color.parseColor("#4CAF50"))
            } else {
                textView.setTextColor(Color.parseColor("#F44336"))
            }

            container.addView(textView)
        }

        // 🔹 ACTUALIZAR TOTALES
        tvGastos.text = "$$totalGastos"

        val balance = totalIngresos - totalGastos // 👈 NUEVO
        tvBalance.text = "$$balance" // 👈 NUEVO
    }
}