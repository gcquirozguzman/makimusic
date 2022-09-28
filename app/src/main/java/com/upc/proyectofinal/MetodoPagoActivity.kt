package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MetodoPagoActivity : AppCompatActivity() {

    private lateinit var btnPagar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metodo_pago)
        asignarReferencias()
    }

    fun asignarReferencias(){
        btnPagar=findViewById(R.id.btnPagar)
        btnPagar.setOnClickListener{
            val intent = Intent(this, ResumenActivity::class.java)
            startActivity(intent)
        }
    }

}