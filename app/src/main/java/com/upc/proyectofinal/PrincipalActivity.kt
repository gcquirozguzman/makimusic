package com.upc.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.upc.proyectofinal.entidad.Makis
import kotlinx.coroutines.Job

class PrincipalActivity : AppCompatActivity() {

    private lateinit var btnPerfilUsuario: ImageView
    private lateinit var btnOrdenarAhora: Button
    private lateinit var txtNombreMaki1:TextView
    private lateinit var txtNombreMaki2:TextView
    private lateinit var txtNombreMaki3:TextView
    private lateinit var txtPrecioMaki1:TextView
    private lateinit var txtPrecioMaki2:TextView
    private lateinit var txtPrecioMaki3:TextView
    private lateinit var imageViewM1:ImageView
    private lateinit var imageViewM2:ImageView
    private lateinit var imageViewM3:ImageView
    private var adaptador:AdaptadorMakis?=null
    private lateinit var messagesListener: ValueEventListener
    private val listaMakis:MutableList<Makis> = ArrayList()
    private val db = Firebase.database
    val referencia = db.getReference("makis")
    private var job: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        asignarReferencias()
        mostrarMakis()

        //val intent = intent
        //val hola = intent.getStringExtra("photoUrl")
        //Picasso.get().load(imagenFoto).into(btnPerfilUsuario);

    }


    private fun mostrarMakis(){


        var contador = 0;

        messagesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaMakis.clear()
                snapshot.children.forEach{ item->

                    if(contador == 2){
                        txtNombreMaki1.text = item.child("nombre").getValue().toString()
                        txtPrecioMaki1.text = item.child("precio").getValue().toString()
                        contador++
                    }

                    if(contador == 1){
                        txtNombreMaki2.text = item.child("nombre").getValue().toString()
                        txtPrecioMaki2.text = item.child("precio").getValue().toString()
                        contador++
                    }

                    if(contador == 0){
                        txtNombreMaki3.text = item.child("nombre").getValue().toString()
                        txtPrecioMaki3.text = item.child("precio").getValue().toString()
                        contador++
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("==>", error.message)
            }
        }
        referencia.addValueEventListener(messagesListener)



    }

    fun asignarReferencias(){

        txtNombreMaki1 = findViewById(R.id.txtNombreMaki1)
        txtNombreMaki2 = findViewById(R.id.txtNombreMaki2)
        txtNombreMaki3 = findViewById(R.id.txtNombreMaki3)

        txtPrecioMaki1 = findViewById(R.id.txtPrecioMaki1)
        txtPrecioMaki2 = findViewById(R.id.txtPrecioMaki2)
        txtPrecioMaki3 = findViewById(R.id.txtPrecioMaki3)

        imageViewM1 = findViewById(R.id.imageViewM1)
        imageViewM2 = findViewById(R.id.imageViewM2)
        imageViewM3 = findViewById(R.id.imageViewM3)

        btnPerfilUsuario=findViewById(R.id.btnPerfil)
        btnPerfilUsuario.setOnClickListener{
            val intent = Intent(this, MantenimientoActivity::class.java)
            startActivity(intent)
        }

        btnOrdenarAhora=findViewById(R.id.btnOrdenarAhora)
        btnOrdenarAhora.setOnClickListener{
            val intent = Intent(this, GenerarPedidoActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        mostrarMakis()
    }

}