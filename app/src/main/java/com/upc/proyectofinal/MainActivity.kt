package com.upc.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private var id:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        asignarReferencias();
        recuperarDatos()
    }

    private fun recuperarDatos(){
        if (intent.hasExtra("id")){
            modificar=true
            id = intent.getStringExtra("id")?.toInt() ?: 0
            txtNombre.setText(intent.getStringExtra("nombre"))
            txtSalsa.setText(intent.getStringExtra("salsa"))
            txtCantidad.setText(intent.getStringExtra("cantidad"))
            txtPrecio.setText(intent.getStringExtra("precio"))
            txtTotal.setText(intent.getStringExtra("total"))
        }
    }

    private fun asignarReferencias(){
        txtNombre=findViewById(R.id.txtNombre)
        txtSalsa=findViewById(R.id.txtSalsa)
        txtCantidad=findViewById(R.id.txtCantidad)
        txtPrecio=findViewById(R.id.txtPrecio)
        txtTotal=findViewById(R.id.txtTotal)
        btnPedido=findViewById(R.id.btnPedido)

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
            val obj=Makis()
            if (modificar){
                obj.id = id
            }
            obj.nombre=nombre
            obj.salsa=salsa
            obj.cantidad=cantidad.toInt()
            obj.precio=precio.toDouble()
            obj.total=total.toDouble()

            var mensaje=""
            if (modificar){
                mensaje = makisDAO.modificarMakis(obj)
            }else{
                mensaje= makisDAO.registrarMakis(obj)
            }
            mostrarMensaje(mensaje)
            limpiarCampos()
        }
    }
    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar",null)
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