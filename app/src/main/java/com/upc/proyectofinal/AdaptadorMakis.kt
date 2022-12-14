package com.upc.proyectofinal

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upc.proyectofinal.entidad.Makis


class AdaptadorMakis : RecyclerView.Adapter<AdaptadorMakis.AdaptadorViewHolder> (){

    private var listaMakis:List<Makis> = ArrayList()
    private lateinit var context: Context

    private var onClickDeleteItem:((Makis)-> Unit)? = null
    fun setOnClickDeleteItem(callback:(Makis)->Unit){
        this.onClickDeleteItem = callback
    }

    fun contexto(context: Context){
        this.context=context
    }

    fun addItems(items:List<Makis>){
        this.listaMakis = items
    }

    class AdaptadorViewHolder (var view: View):RecyclerView.ViewHolder(view){
        private var nombre=view.findViewById<TextView>(R.id.filaNombre)
        private var salsa=view.findViewById<TextView>(R.id.filaSalsa)
        private var porciones=view.findViewById<TextView>(R.id.filaPorciones)
        private var precio=view.findViewById<TextView>(R.id.filaPrecio)
        private var descuento=view.findViewById<TextView>(R.id.filaDescuento)
        private var imagen=view.findViewById<ImageView>(R.id.filaImagen)

        var filaEditar = view.findViewById<ImageButton>(R.id.btnEditar)
        var filaEliminar = view.findViewById<ImageButton>(R.id.btnEliminar)

        fun bindView(makis:Makis){
            nombre.text=makis.nombre
            salsa.text=makis.salsa
            porciones.text=makis.porciones.toString()
            precio.text=makis.precio.toString()
            descuento.text=makis.descuento.toString()

            val food: ByteArray = makis.imagen.toByteArray()
            val valor = BitmapFactory.decodeByteArray(food, 0, food.size)
            imagen.setImageBitmap(valor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdaptadorViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila,parent,false)
    )

    override fun onBindViewHolder(holder: AdaptadorViewHolder, position: Int) {
        val makisItem=listaMakis[position]
        holder.bindView(makisItem)
        holder.filaEditar.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("id",listaMakis[position].id.toString())
            intent.putExtra("nombre", listaMakis[position].nombre)
            intent.putExtra("salsa", listaMakis[position].salsa)
            intent.putExtra("porciones", listaMakis[position].porciones.toString())
            intent.putExtra("precio", listaMakis[position].precio.toString())
            intent.putExtra("descuento", listaMakis[position].descuento.toString())

            context.startActivity(intent)
        }
        holder.filaEliminar.setOnClickListener{
            onClickDeleteItem?.invoke(makisItem)
        }
    }

    override fun getItemCount(): Int {
        return listaMakis.size
    }
}