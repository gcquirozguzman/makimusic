package com.upc.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class BienvenidaActivity : AppCompatActivity() {

    private lateinit var btnBienvenida: ImageButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)
        asignarReferencias()
    }

    fun asignarReferencias(){

        btnBienvenida=findViewById(R.id.btnBienvenida)
        progressBar=findViewById(R.id.progressBar)

        btnBienvenida.setOnClickListener {
            redirigirRegistro()
        }

    }

    fun redirigirRegistro(){
        progressBar.setVisibility(View.VISIBLE)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        progressBar.setVisibility(View.INVISIBLE)
    }
}