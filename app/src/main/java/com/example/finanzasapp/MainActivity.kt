package com.example.finanzasapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //CARGAR DATOS GUARDADOS
        DataManager.load(this)

        setContentView(R.layout.activity_main)

        val titulo = findViewById<TextView>(R.id.titulo)
        val loginButton = findViewById<Button>(R.id.btnLogin)

        // TEXTO CON COLORES
        val texto = "FinanzasApp"
        val spannable = SpannableString(texto)

        spannable.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0, 8,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(Color.BLUE),
            8, 9,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(Color.GREEN),
            9, 11,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        titulo.text = spannable

        // NAVEGACIÓN
        loginButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}