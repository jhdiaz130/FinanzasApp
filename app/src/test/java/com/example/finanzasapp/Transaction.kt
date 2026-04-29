package com.example.finanzasapp

import android.R

data class Transaction(
    val tipo: R.string,
    val categoria: R.string,
    val monto: Int
)