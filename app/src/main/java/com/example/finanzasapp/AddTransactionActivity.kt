package com.example.finanzasapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTransactionActivity : AppCompatActivity() {

    private var tipoSeleccionado = "gasto"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        val etMonto = findViewById<EditText>(R.id.etMonto)
        val etCategoria = findViewById<EditText>(R.id.etCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val btnIngreso = findViewById<Button>(R.id.btnIngreso)
        val btnGasto = findViewById<Button>(R.id.btnGasto)

        // 🔥 ESTADO INICIAL
        btnGasto.setBackgroundColor(Color.parseColor("#1565C0"))
        btnIngreso.setBackgroundColor(Color.GRAY)

        // 🔹 BOTÓN INGRESO
        btnIngreso.setOnClickListener {
            tipoSeleccionado = "ingreso"
            btnIngreso.setBackgroundColor(Color.parseColor("#4CAF50"))
            btnGasto.setBackgroundColor(Color.GRAY)
        }

        // 🔹 BOTÓN GASTO
        btnGasto.setOnClickListener {
            tipoSeleccionado = "gasto"
            btnGasto.setBackgroundColor(Color.parseColor("#1565C0"))
            btnIngreso.setBackgroundColor(Color.GRAY)
        }

        // 🔹 GUARDAR
        btnGuardar.setOnClickListener {

            val montoTexto = etMonto.text.toString()
            val categoriaTexto = etCategoria.text.toString()

            if (montoTexto.isNotEmpty() && categoriaTexto.isNotEmpty()) {

                // 🔥 FECHA ACTUAL
                val fechaActual = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(Date())

                val nueva = Transaction(
                    tipo = tipoSeleccionado,
                    categoria = categoriaTexto,
                    monto = montoTexto.toInt(),
                    fecha = fechaActual
                )

                DataManager.transactions.add(nueva)

                // GUARDAR
                DataManager.save(this)

                finish()
            }
        }
    }
}