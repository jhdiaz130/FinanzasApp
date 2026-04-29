package com.example.finanzasapp


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        val monto = findViewById<EditText>(R.id.etMonto)
        val categoria = findViewById<EditText>(R.id.etCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {

            val montoTexto = monto.text.toString()
            val categoriaTexto = categoria.text.toString()

            if (montoTexto.isNotEmpty() && categoriaTexto.isNotEmpty()) {

                val nueva = Transaction(
                    tipo = "gasto",
                    categoria = categoriaTexto,
                    monto = montoTexto.toInt()
                )

                DataManager.transactions.add(nueva)

                finish()
            }
        }
    }
}