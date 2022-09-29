package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var btnPagar: Button
    private lateinit var txtMakiPedido: TextView
    private lateinit var txtMakiPedido2: TextView
    private lateinit var txtMakiPedido3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)
        asignarReferencias()

        val nombre = intent.getStringExtra("nombre")
        val cantidad = intent.getStringExtra("cantidad")
        val observacion = intent.getStringExtra("observacion")

        txtMakiPedido.text = nombre
        txtMakiPedido2.text = cantidad
        txtMakiPedido3.text = observacion

        intent.putExtra("nombre", txtMakiPedido.text)
        intent.putExtra("cantidad", txtMakiPedido2.text)
        intent.putExtra("observacion", txtMakiPedido3.text)

    }

    fun asignarReferencias(){

        btnPagar=findViewById(R.id.btnPagar)
        txtMakiPedido=findViewById(R.id.txtMakiPedido)
        txtMakiPedido2=findViewById(R.id.txtMakiPedido2)
        txtMakiPedido3=findViewById(R.id.txtMakiPedido3)

        btnPagar.setOnClickListener{
            val intent = Intent(this, MetodoPagoActivity::class.java)
            startActivity(intent)
        }
    }

}