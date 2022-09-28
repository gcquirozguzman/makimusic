package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class PrincipalActivity : AppCompatActivity() {

    private lateinit var btnPerfilUsuario: ImageView
    private lateinit var btnOrdenarAhora: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        asignarReferencias()
    }

    fun asignarReferencias(){

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

}