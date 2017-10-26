package fr.com.mbds.etudiant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView nom;
    TextView getNom;
    TextView prenom;
    TextView getPrenom;
    ImageView img;
    Button buttonShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nom=(TextView)findViewById(R.id.nomP);
        prenom=(TextView)findViewById(R.id.prenomP);
        getNom=(TextView)findViewById(R.id.getNomP);
        getPrenom=(TextView)findViewById(R.id.getPrenomP);
        img=(ImageView)findViewById(R.id.imagePersonne);
        buttonShare=(Button)findViewById(R.id.buttonShare);

        Intent i = getIntent();
        Personne p = (Personne)i.getSerializableExtra("personne");

        img.setImageBitmap(p.getImage());
        getNom.setText(p.getNom());
        getPrenom.setText(p.getPrenom());
    }
}
