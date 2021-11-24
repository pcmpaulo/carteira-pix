package com.example.carteirapix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBController {

    private SQLiteDatabase db;
    private DBCore dbCore;

    public DBController(Context context){
        dbCore = new DBCore(context);
    }

    public String insertData(String name, String pix){
        ContentValues valores;
        long resultado;

        db = dbCore.getWritableDatabase();
        valores = new ContentValues();
        valores.put(DBCore.NAME, name);
        valores.put(DBCore.PIX, pix);

        resultado = db.insert(DBCore.TABLE_NAME, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public Cursor listData(){
        Cursor cursor;
        String[] campos =  {dbCore.ID,dbCore.NAME,dbCore.PIX};
        db = dbCore.getReadableDatabase();
        cursor = db.query(
                dbCore.TABLE_NAME,
                campos,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor requestData(int id){
        Cursor cursor;
        String[] campos =  {dbCore.ID,dbCore.NAME,dbCore.PIX};
        String where = DBCore.ID + "=" + id;
        db = dbCore.getReadableDatabase();
        cursor = db.query(
                DBCore.TABLE_NAME,
                campos,
                where,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void updateData(int id, String name, String pix){
        ContentValues valores;
        String where;

        db = dbCore.getWritableDatabase();

        where = DBCore.ID + "=" + id;

        valores = new ContentValues();
        valores.put(DBCore.NAME, name);
        valores.put(DBCore.PIX, pix);

        db.update(DBCore.TABLE_NAME,valores,where,null);
        db.close();
    }

    public void deleteData(int id){
        String where = DBCore.ID + "=" + id;
        db = dbCore.getReadableDatabase();
        db.delete(DBCore.TABLE_NAME,where,null);
        db.close();
    }
}
