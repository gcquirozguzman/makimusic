package com.upc.proyectofinal.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.upc.proyectofinal.entidad.Makis
import com.upc.proyectofinal.util.MakisBD
import java.lang.Exception

class MakisDAO (context: Context){
    private var makisBD:MakisBD = MakisBD(context)

    fun registrarMakis(makis: Makis):String{
        var respuesta=""
        val db=makisBD.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("nombre",makis.nombre)
            valores.put("salsa",makis.salsa)
            valores.put("porciones",makis.porciones)
            valores.put("precio",makis.precio);
            valores.put("descuento",makis.descuento)
            var resultado=db.insert("makis", null, valores)
            if (resultado==-1L){
                respuesta="Error al insertar"
            }else{
                respuesta="Se registro correctamente"
            }
        }catch (e:Exception){
            respuesta=e.message.toString()
        }finally {
            db.close()
        }
        return respuesta
    }

    fun modificarMakis(makis: Makis):String{
        var respuesta=""
        val db=makisBD.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("nombre",makis.nombre)
            valores.put("salsa",makis.salsa)
            valores.put("porciones",makis.porciones)
            valores.put("precio",makis.precio);
            valores.put("descuento",makis.descuento)
            var resultado=db.update("makis", valores, "id="+makis.id, null)
            if (resultado==-1){
                respuesta="Error al actualizar"
            }else{
                respuesta="Se modifico correctamente"
            }
        }catch (e:Exception){
            respuesta=e.message.toString()
        }finally {
            db.close()
        }
        return respuesta
    }

    fun cargarMakis():ArrayList<Makis>{
        val listaMakis:ArrayList<Makis> = ArrayList()
        val query = "SELECT * FROM makis"
        val db = makisBD.readableDatabase
        val cursor:Cursor

        try {
            cursor=db.rawQuery(query,null)
            if(cursor.count>0){
                cursor.moveToFirst()
                do {
                    val id:String = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                    val nombre:String=cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val salsa:String=cursor.getString(cursor.getColumnIndexOrThrow("salsa"))
                    val porciones:Int=cursor.getInt(cursor.getColumnIndexOrThrow("porciones"))
                    val precio:Double=cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
                    val descuento:Double=cursor.getDouble(cursor.getColumnIndexOrThrow("descuento"))

                    val makis=Makis(
                        id,
                        nombre,
                        salsa,
                        porciones,
                        precio,
                        descuento
                    )

                    listaMakis.add(makis)
                }while (cursor.moveToNext())
            }
        }catch (e:Exception){

        }finally{
            db.close()
        }
        return listaMakis
    }

    fun eliminarMakis(id:String):String{
        var respuesta = ""
        val db = makisBD.writableDatabase
        try {
            var res=db.delete("makis", "id= "+id, null)
            if (res==-1){
                respuesta="Error, no se elimino"
            }else{
                respuesta="Se elimino"
            }
        }catch (e:Exception){

        }finally{
        db.close()
        }
        return respuesta
    }
}