package com.upc.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MantenimientoActivity : AppCompatActivity() {

    private lateinit var btnCerrar: Button
    private lateinit var cvMantMakis: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimiento)

        asignarReferencias()



    }

    fun asignarReferencias(){

        btnCerrar=findViewById(R.id.btnCerrarSesion)
        btnCerrar.setOnClickListener{
            GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            ).signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        cvMantMakis=findViewById(R.id.cvMantenimientoMakis)
        cvMantMakis.setOnClickListener{
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        }

    }
}