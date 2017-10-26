package fr.com.mbds.etudiant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jperk on 25/10/2017.
 */

public class PersonneDBHelper extends SQLiteOpenHelper {
    public static final String TABLE="Personne";
    public static final String id ="id";
    public static final String nom ="nom";
    public static final String prenom ="prenom";
    public static final String photo ="photo";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "personne.db";
    private static final String CREATE_TABLE=" CREATE TABLE "+TABLE+" ("+
            id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +nom+" TEXT, "
            +prenom+" TEXT, "
            +photo+" BLOB "+");";
    public PersonneDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        // Create tables again
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE);
        onCreate(db);
    }
}
