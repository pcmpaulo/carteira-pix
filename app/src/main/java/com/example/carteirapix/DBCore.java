package com.example.carteirapix;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCore extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco.db";
    public static final String TABLE_NAME = "PIX_TABLE";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String PIX = "pix";
    private static final int VERSAO = 1;

    public DBCore(Context context) {
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE_NAME+"("
                + ID + " integer primary key autoincrement,"
                + NAME + " text,"
                + PIX + " text"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
