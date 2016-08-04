package br.com.ekbt.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rm74043 on 03/08/2016.
 */
public class DBProduto extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Loja.db";
    private static final int DATABASE_VERSION = 2;

    public DBProduto(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE TBL_PRODUTO ( " +
                     "ID INTEGER PRIMARY KEY, " +
                     "NM_PRODUTO TEXT, " +
                     "VL_PRECO_REAL )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS TBL_PRODUTO";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
