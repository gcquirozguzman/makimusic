package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.upc.proyectofinal.modelo.MakisDAO

class ListaActivity : AppCompatActivity() {

    private lateinit var btnAbrir:FloatingActionButton
    private lateinit var rvMakis: RecyclerView
    private var adaptador:AdaptadorMakis?=null

    private var makisDAO: MakisDAO= MakisDAO(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        asignarReferencias()
        mostrarMakis()
    }

    private fun asignarReferencias(){

        btnAbrir=findViewById(R.id.btnAbrir)
        btnAbrir.setOnClickListener{
            val intent=Intent(baseContext,MainActivity::class.java)
            startActivity(intent)
        }
        rvMakis = findViewById(R.id.rvMakis)
        rvMakis.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorMakis()
        rvMakis.adapter = adaptador

        adaptador?.setOnClickDeleteItem {
            eliminar(it.id)
        }
    }

    fun eliminar(id:Int){
        if(id==null){
            return
        }
        val ventana = AlertDialog.Builder(this)
        ventana.setMessage("Desea eliminar la orden de makis?")
        ventana.setCancelable(true)
        ventana.setNegativeButton("No", null)
        ventana.setPositiveButton("Si"){
                dialog, _->
            var mensaje = makisDAO.eliminarMakis(id)
            mostrarMakis()
            dialog.dismiss()
            mostrarMensaje(mensaje)
        }
        ventana.create().show()
    }

    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar",null)
        ventana.create().show()
    }

    private fun mostrarMakis(){
        val listaMakis=makisDAO.cargarMakis()
        adaptador?.contexto(this)
        adaptador?.addItems(listaMakis)
    }
}