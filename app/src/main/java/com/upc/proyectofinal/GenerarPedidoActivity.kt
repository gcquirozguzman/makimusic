package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GenerarPedidoActivity : AppCompatActivity() {

    private lateinit var btnPedido: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_pedido)
        asignarReferencias()
    }
    fun asignarReferencias(){
        btnPedido=findViewById(R.id.btnPedido)
        btnPedido.setOnClickListener{
            val intent = Intent(this, DetallePedidoActivity::class.java)
            startActivity(intent)
        }
    }

}