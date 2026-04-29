package com.example.finanzasapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvGastos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        tvGastos = findViewById(R.id.tvGastosMes) // 👈 este lo vamos a crear

        fab.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        var totalGastos = 0

        for (t in DataManager.transactions) {
            if (t.tipo == "gasto") {
                totalGastos += t.monto
            }
        }

        tvGastos.text = "$" + totalGastos.toString()
    }
}