package com.upc.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.upc.proyectofinal.entidad.Makis
import com.upc.proyectofinal.modelo.MakisDAO


class ListaActivity : AppCompatActivity() {

    private lateinit var btnAbrir:FloatingActionButton
    private lateinit var btnCerrar:Button
    private lateinit var rvMakis: RecyclerView
    private var adaptador:AdaptadorMakis?=null
    private var makisDAO: MakisDAO= MakisDAO(this)

    private val db = Firebase.database
    private lateinit var messagesListener: ValueEventListener
    val referencia = db.getReference("makis")
    private val listaMakis:MutableList<Makis> = ArrayList()

    lateinit var mGoogleSignInClient: GoogleSignInClient

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

        btnCerrar=findViewById(R.id.btnCerrar)
        btnCerrar.setOnClickListener{
            GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            ).signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun eliminar(id:String){
        if(id==null){
            return
        }
        val ventana = AlertDialog.Builder(this)
        ventana.setMessage("Desea eliminar la orden de makis?")
        ventana.setCancelable(true)
        ventana.setNegativeButton("No", null)
        ventana.setPositiveButton("Si"){
                dialog, _->
            referencia.child(id).removeValue()
            mostrarMakis()
            dialog.dismiss()
            mostrarMensaje("Se eliminó")
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
        //val listaMakis=makisDAO.cargarMakis()
        adaptador?.contexto(this)
        //adaptador?.addItems(listaMakis)

        messagesListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listaMakis.clear()
                snapshot.children.forEach{ item->
                    val maki:Makis = Makis(
                        item.key.toString(),
                        item.child("nombre").getValue().toString(),
                        item.child("salsa").getValue().toString(),
                        item.child("cantidad").getValue().toString().toInt(),
                        item.child("precio").getValue().toString().toDouble(),
                        item.child("total").getValue().toString().toDouble()
                    )
                    maki?.let { listaMakis.add(it) }
                }
                Log.d("==>", listaMakis.size.toString())
                rvMakis.layoutManager = LinearLayoutManager(this@ListaActivity)
                //rvMakis.adapter = AdaptadorMakis(listaMakis)

                adaptador?.addItems(listaMakis)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("==>", error.message)
            }
        }
        referencia.addValueEventListener(messagesListener)



    }

}