package fr.com.mbds.etudiant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.Serializable;

/**
 * Created by jperk on 23/10/2017.
 */

public class Personne implements Serializable{
    public int id;
    public String nom;
    public String prenom;
    public Bitmap image;

    public Personne(){

    }
    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        FileInputStream in;
        BufferedInputStream buf;
        try {
            in = new FileInputStream("student.png");
            buf = new BufferedInputStream(in);
            this.image = BitmapFactory.decodeStream(buf);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }
        } catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }

    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
