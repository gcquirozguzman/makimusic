package com.upc.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.upc.proyectofinal.entidad.Makis


class GenerarPedidoActivity : AppCompatActivity() {

    private lateinit var btnPedido: Button
    private lateinit var textView25: EditText
    private lateinit var txtCantidad: EditText
    private lateinit var txtObservacion: EditText


    private lateinit var messagesListener: ValueEventListener
    private val listaMakis:MutableList<Makis> = ArrayList()
    private val db = Firebase.database
    val referencia = db.getReference("makis")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_pedido)
        asignarReferencias()

    }

    fun asignarReferencias(){

        btnPedido=findViewById(R.id.btnPedido)
        textView25=findViewById(R.id.textView25)
        txtCantidad=findViewById(R.id.txtCantidad)
        txtObservacion=findViewById(R.id.txtObservacion)

        btnPedido.setOnClickListener{
            val intent = Intent(this, DetallePedidoActivity::class.java)

            intent.putExtra("nombre", textView25.text)
            intent.putExtra("cantidad", txtCantidad.text)
            intent.putExtra("observacion", txtObservacion.text)

            startActivity(intent)
        }
    }

}