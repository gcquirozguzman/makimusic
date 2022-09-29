package com.upc.proyectofinal

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.upc.proyectofinal.entidad.Makis
import com.upc.proyectofinal.modelo.MakisDAO
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    var REQUEST_CODE = 200

    private lateinit var txtNombre:EditText
    private lateinit var txtSalsa:EditText
    private lateinit var txtCantidad:EditText
    private lateinit var txtPrecio:EditText
    private lateinit var txtTotal:EditText
    private lateinit var btnPedido:Button
    private lateinit var imgMakis:ImageView

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
        imgMakis=findViewById(R.id.imgMakis)

        if (modificar){
            btnPedido.setText("Actualizar Pedido")
        } else {
            btnPedido.setText("Registrar Pedido")
        }


        btnPedido.setOnClickListener {
            registrarMakis()
        }

        imgMakis.setOnClickListener {
            openGalleryForImages()
        }


    }
    private fun registrarMakis(){
        //Toast.makeText(this, "Correcto", Toast.LENGTH_LONG).show()
        val nombre=txtNombre.text.toString()
        val salsa=txtSalsa.text.toString()
        val cantidad=txtCantidad.text.toString()
        val precio=txtPrecio.text.toString()
        val total=txtTotal.text.toString()

        val bitmap = (imgMakis.getDrawable() as BitmapDrawable).bitmap
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        val imagen = bos.toByteArray();

        if (nombre.isEmpty()||salsa.isEmpty()||cantidad.isEmpty()||precio.isEmpty()||total.isEmpty()){
            Toast.makeText(this,"Campos vacios",Toast.LENGTH_LONG).show()
        }else{
            val obj=Makis(
                id,
                nombre,
                salsa,
                cantidad.toInt(),
                precio.toDouble(),
                total.toDouble(),
                imagen.contentToString()
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

    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures")
                , REQUEST_CODE
            )
        }
        else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){

            // if multiple images are selected
            if (data?.getClipData() != null) {
                var count = data.clipData?.itemCount

                for (i in 0..count!! - 1) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    imgMakis.setImageURI(imageUri)
                    //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                }

            } else if (data?.getData() != null) {
                // if single image is selected

                var imageUri: Uri = data.data!!
                imgMakis.setImageURI(imageUri)
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview

            }
        }
    }

}