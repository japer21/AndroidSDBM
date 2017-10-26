package fr.com.mbds.etudiant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import utils.DbBitmapUtility;

/**
 * Created by jperk on 25/10/2017.
 */

public class PersonneDBRepository {
    private PersonneDBHelper dbHelper;
    public PersonneDBRepository(Context ctx) {
        this.dbHelper=new PersonneDBHelper(ctx);
    }
    public int insertPersonne(Personne personne) throws SQLException{
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PersonneDBHelper.nom,personne.getNom());
        contentValues.put(PersonneDBHelper.prenom,personne.getPrenom());

        contentValues.put(PersonneDBHelper.photo,DbBitmapUtility.getBytes(personne.getImage()));
        long id_p=db.insert(PersonneDBHelper.TABLE,null,contentValues);
        db.close();
        return (int)id_p;
    }
    public int deletePersonne(int id) throws SQLException{
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id_p=db.delete(PersonneDBHelper.TABLE,PersonneDBHelper.id +" = ?",new String[]{String.valueOf(id)});
        db.close();
        return id_p;
    }
    public void updatePersonne(Personne personne){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PersonneDBHelper.nom,personne.getNom());
        contentValues.put(PersonneDBHelper.prenom,personne.getPrenom());
        contentValues.put(PersonneDBHelper.photo,DbBitmapUtility.getBytes(personne.getImage()));
        db.update(PersonneDBHelper.TABLE,contentValues,PersonneDBHelper.id +" = ?",new String[]{String.valueOf(personne.getId())});
        db.close();

    }

    public ArrayList<Personne> getPersonnes(){
        ArrayList<Personne> l = new ArrayList<Personne>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String QUERY ="SELECT " +PersonneDBHelper.nom+
                ", " +PersonneDBHelper.prenom+
                ", " +PersonneDBHelper.photo+" FROM "+PersonneDBHelper.TABLE+
                " ;";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.moveToFirst()){
            do {
                Personne personne = new Personne(cursor.getString(cursor.getColumnIndex(PersonneDBHelper.nom)),cursor.getString(cursor.getColumnIndex(PersonneDBHelper.prenom)));
                //personne.put("nom",cursor.getString(cursor.getColumnIndex(PersonneDBHelper.nom)));
                //personne.put("prenom",cursor.getString(cursor.getColumnIndex(PersonneDBHelper.prenom)));
                personne.setImage(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(PersonneDBHelper.photo))));
                l.add(personne);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return l;
    }
    public ArrayList<HashMap<String,Object>> listPersonnes (){
        ArrayList<HashMap<String, Object>> personnesList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String QUERY ="SELECT " +PersonneDBHelper.nom+
                ", " +PersonneDBHelper.prenom+
                ", " +PersonneDBHelper.photo+", FROM "+PersonneDBHelper.TABLE+
                " ;";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.moveToFirst()){
            do {
                HashMap<String,Object> personne = new HashMap<>();
                personne.put("nom",cursor.getString(cursor.getColumnIndex(PersonneDBHelper.nom)));
                personne.put("prenom",cursor.getString(cursor.getColumnIndex(PersonneDBHelper.prenom)));
                personne.put("photo",DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(PersonneDBHelper.photo))));
                personnesList.add(personne);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return personnesList;
    }
    public Personne getPersonneById(int id_p){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String QUERY = "SELECT "+PersonneDBHelper.nom +
                ", " +PersonneDBHelper.prenom +
                ", " +PersonneDBHelper.photo +" FROM "+PersonneDBHelper.TABLE+" WHERE "+PersonneDBHelper.id+" =?"+
                ";";
        Cursor cursor=db.rawQuery(QUERY,new String[]{ String.valueOf(id_p)});
        Personne p=new Personne();
        if(cursor.moveToFirst()){
            do {
                p.setNom(cursor.getString(cursor.getColumnIndex(PersonneDBHelper.nom)));
                p.setPrenom(cursor.getString(cursor.getColumnIndex(PersonneDBHelper.prenom)));
                p.setImage(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(PersonneDBHelper.photo))));

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return p;
    }
}
