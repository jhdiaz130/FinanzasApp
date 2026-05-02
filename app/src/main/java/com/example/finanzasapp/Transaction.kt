package com.example.finanzasapp

data class Transaction(
    val tipo: String,
    val categoria: String,
    val monto: Int,
    val fecha: String
)