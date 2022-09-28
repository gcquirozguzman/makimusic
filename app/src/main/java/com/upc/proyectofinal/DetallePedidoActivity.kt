package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var btnPagar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)
        asignarReferencias()
    }

    fun asignarReferencias(){
        btnPagar=findViewById(R.id.btnPagar)
        btnPagar.setOnClickListener{
            val intent = Intent(this, MetodoPagoActivity::class.java)
            startActivity(intent)
        }
    }

}