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
    private lateinit var tvIngresos: TextView
    private lateinit var tvBalance: TextView
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 CARGAR DATOS
        DataManager.load(this)

        setContentView(R.layout.activity_dashboard)

        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)

        tvGastos = findViewById(R.id.tvGastosMes)
        tvIngresos = findViewById(R.id.tvIngresosMes)
        tvBalance = findViewById(R.id.tvBalance)
        container = findViewById(R.id.containerMovimientos)

        fab.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
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

        for ((index, t) in DataManager.transactions.withIndex()) {

            if (t.tipo == "gasto") {
                totalGastos += t.monto
            } else {
                totalIngresos += t.monto
            }

            val textView = TextView(this)

            val signo = if (t.tipo == "ingreso") "+" else "-"

            // 🔥 AQUÍ ESTÁ LO QUE BUSCABAS (CON FECHA)
            textView.text = "$signo $${t.monto} | ${t.categoria} | ${t.fecha}"

            textView.textSize = 16f

            if (t.tipo == "ingreso") {
                textView.setTextColor(Color.parseColor("#4CAF50"))
            } else {
                textView.setTextColor(Color.parseColor("#F44336"))
            }

            // 🔥 ELIMINAR + GUARDAR
            textView.setOnLongClickListener {
                DataManager.transactions.removeAt(index)
                DataManager.save(this)
                actualizarUI()
                true
            }

            container.addView(textView)
        }

        val balance = totalIngresos - totalGastos

        tvGastos.text = "$$totalGastos"
        tvIngresos.text = "$$totalIngresos"
        tvBalance.text = "$$balance"
    }
}