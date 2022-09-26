package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.upc.proyectofinal.modelo.MakisDAO
import com.upc.proyectofinal.entidad.Makis

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre:EditText
    private lateinit var txtSalsa:EditText
    private lateinit var txtCantidad:EditText
    private lateinit var txtPrecio:EditText
    private lateinit var txtTotal:EditText
    private lateinit var btnPedido:Button

    var makisDAO:MakisDAO= MakisDAO(this)
    private var modificar:Boolean = false
    private var id:String="ID"
    private val db = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asignarReferencias()
        recuperarDatos()
    }

    private fun recuperarDatos(){
        if (intent.hasExtra("id")){
            modificar=true
            id = intent.getStringExtra("id")?.toString() ?: "0"
            txtNombre.setText(intent.getStringExtra("nombre"))
            txtSalsa.setText(intent.getStringExtra("salsa"))
            txtCantidad.setText(intent.getStringExtra("porciones"))
            txtPrecio.setText(intent.getStringExtra("precio"))
            txtTotal.setText(intent.getStringExtra("descuento"))
            btnPedido.setText("Actualizar Pedido")
        }
    }

    private fun asignarReferencias(){
        txtNombre=findViewById(R.id.txtNombre)
        txtSalsa=findViewById(R.id.txtSalsa)
        txtCantidad=findViewById(R.id.txtCantidad)
        txtPrecio=findViewById(R.id.txtPrecio)
        txtTotal=findViewById(R.id.txtTotal)
        btnPedido=findViewById(R.id.btnPedido)

        if (modificar){
            btnPedido.setText("Actualizar Pedido")
        } else {
            btnPedido.setText("Registrar Pedido")
        }


        btnPedido.setOnClickListener {
            registrarMakis()
        }


    }
    private fun registrarMakis(){
        //Toast.makeText(this, "Correcto", Toast.LENGTH_LONG).show()
        val nombre=txtNombre.text.toString()
        val salsa=txtSalsa.text.toString()
        val cantidad=txtCantidad.text.toString()
        val precio=txtPrecio.text.toString()
        val total=txtTotal.text.toString()

        if (nombre.isEmpty()||salsa.isEmpty()||cantidad.isEmpty()||precio.isEmpty()||total.isEmpty()){
            Toast.makeText(this,"Campos vacios",Toast.LENGTH_LONG).show()
        }else{
            val obj=Makis(
                id,
                nombre,
                salsa,
                cantidad.toInt(),
                precio.toDouble(),
                total.toDouble()
            )

            var mensaje=""
            val referencia = db.getReference("makis")

            if (modificar){
                //mensaje = makisDAO.modificarMakis(obj)
                //Log.d("==>", id)
                referencia.child(id).setValue(obj)
                mostrarMensaje("Se actualizó correctamente")
            }else{
                referencia.child(referencia.push().key.toString()).setValue(obj)
                mostrarMensaje("Se registró correctamente")
            }

            limpiarCampos()
        }
    }
    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar") {dialog, which->
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        }
        ventana.create().show()
    }

    private fun limpiarCampos(){
        txtNombre.setText("")
        txtSalsa.setText("")
        txtCantidad.setText("")
        txtPrecio.setText("")
        txtTotal.setText("")
    }
}