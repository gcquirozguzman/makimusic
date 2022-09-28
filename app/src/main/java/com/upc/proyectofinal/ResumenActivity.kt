package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ResumenActivity : AppCompatActivity() {

    private lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)
        asignarReferencias()
    }

    fun asignarReferencias(){
        btnRegresar=findViewById(R.id.btnRegresar)
        btnRegresar.setOnClickListener{
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        }
    }
}