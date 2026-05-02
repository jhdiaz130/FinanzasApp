package com.example.finanzasapp

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddTransactionActivity : AppCompatActivity() {

    private var tipoSeleccionado = "gasto"
    private var indexEditar = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        val etMonto = findViewById<EditText>(R.id.etMonto)
        val etCategoria = findViewById<EditText>(R.id.etCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val btnIngreso = findViewById<Button>(R.id.btnIngreso)
        val btnGasto = findViewById<Button>(R.id.btnGasto)

        // ANIMACIÓN
        animarBoton(btnIngreso)
        animarBoton(btnGasto)

        //  DETECTAR SI ES EDICIÓN
        indexEditar = intent.getIntExtra("index", -1)

        if (indexEditar != -1) {
            val t = DataManager.transactions[indexEditar]

            etMonto.setText(t.monto.toString())
            etCategoria.setText(t.categoria)
            tipoSeleccionado = t.tipo
        }

        actualizarBotones(btnIngreso, btnGasto)

        btnIngreso.setOnClickListener {
            tipoSeleccionado = "ingreso"
            actualizarBotones(btnIngreso, btnGasto)
        }

        btnGasto.setOnClickListener {
            tipoSeleccionado = "gasto"
            actualizarBotones(btnIngreso, btnGasto)
        }

        btnGuardar.setOnClickListener {

            val montoTexto = etMonto.text.toString().trim()
            val categoriaTexto = etCategoria.text.toString().trim()

            // VALIDACIÓN
            if (montoTexto.isEmpty()) {
                etMonto.error = "Ingresa un monto"
                return@setOnClickListener
            }

            if (categoriaTexto.isEmpty()) {
                etCategoria.error = "Ingresa una categoría"
                return@setOnClickListener
            }

            val monto = try {
                montoTexto.toInt()
            } catch (e: Exception) {
                etMonto.error = "Monto inválido"
                return@setOnClickListener
            }

            val nueva = Transaction(
                tipo = tipoSeleccionado,
                categoria = categoriaTexto,
                monto = monto,
                fecha = obtenerFechaActual()
            )

            if (indexEditar != -1) {
                DataManager.transactions[indexEditar] = nueva
            } else {
                DataManager.transactions.add(nueva)
            }

            DataManager.save(this)

            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show()

            finish()
        }
    }

    // BOTONES PRO (COLORES + ESTILO)
    private fun actualizarBotones(btnIngreso: Button, btnGasto: Button) {
        if (tipoSeleccionado == "ingreso") {
            btnIngreso.setBackgroundResource(R.drawable.button_green_rounded)
            btnGasto.setBackgroundResource(R.drawable.button_selector)
        } else {
            btnGasto.setBackgroundResource(R.drawable.button_red_rounded)
            btnIngreso.setBackgroundResource(R.drawable.button_selector)
        }
    }

    // ANIMACIÓN AL PRESIONAR
    private fun animarBoton(boton: Button) {
        boton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.scaleX = 0.95f
                    v.scaleY = 0.95f
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    v.scaleX = 1f
                    v.scaleY = 1f
                }
            }
            false
        }
    }

    private fun obtenerFechaActual(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(java.util.Date())
    }
}