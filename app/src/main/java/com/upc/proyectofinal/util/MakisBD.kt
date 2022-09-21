package com.upc.proyectofinal.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MakisBD (context: Context):SQLiteOpenHelper(context, DATABASENAME, null, DATABASE_VERSION){
    companion object{
        private const val DATABASENAME = "makis.bd"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var sql="CREATE TABLE IF NOT EXISTS makis"+" (id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " nombre TEXT NOT NULL, "+" salsa TEXT NOT NULL, "+" cantidad INTEGER NOT NULL, "+
                " precio DOUBLE NOT NULL,"+" total DOUBLE NOT NULL)"
        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS makis")
        onCreate(p0)
    }
}