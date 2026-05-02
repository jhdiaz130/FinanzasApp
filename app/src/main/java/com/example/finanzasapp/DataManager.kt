package com.example.finanzasapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object DataManager {

    val transactions = mutableListOf<Transaction>()

    //GUARDAR
    fun save(context: Context) {
        val jsonArray = JSONArray()

        for (t in transactions) {
            val obj = JSONObject()
            obj.put("tipo", t.tipo)
            obj.put("categoria", t.categoria)
            obj.put("monto", t.monto)
            obj.put("fecha", t.fecha)
            jsonArray.put(obj)
        }

        val prefs = context.getSharedPreferences("finanzas", Context.MODE_PRIVATE)
        prefs.edit().putString("data", jsonArray.toString()).apply()
    }

    //CARGAR
    fun load(context: Context) {
        val prefs = context.getSharedPreferences("finanzas", Context.MODE_PRIVATE)
        val data = prefs.getString("data", null) ?: return

        transactions.clear()

        val jsonArray = JSONArray(data)

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)

            val t = Transaction(
                tipo = obj.getString("tipo"),
                categoria = obj.getString("categoria"),
                monto = obj.getInt("monto"),
                fecha = obj.optString("fecha", "Sin fecha")
            )

            transactions.add(t)
        }
    }
}