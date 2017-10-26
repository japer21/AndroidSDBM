package fr.com.mbds.etudiant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AjoutPersonne extends AppCompatActivity {
    private final static int REQUEST_IMAGE_CAPTURE=1;
    EditText nom;
    EditText prenom;
    ImageView img;
    Button addImg;
    Button createPersonne;
    Personne p=new Personne();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_personne);
        nom=(EditText)findViewById(R.id.editText2);
        prenom=(EditText)findViewById(R.id.editText3);
        img = (ImageView)findViewById(R.id.imageAjoutPersonne);
        createPersonne=(Button)findViewById(R.id.buttonAjout);
        addImg=(Button)findViewById(R.id.addPhoto);

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        createPersonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonneDBRepository DB = new PersonneDBRepository(getApplicationContext());
                p.setNom(nom.getText().toString());
                p.setPrenom(prenom.getText().toString());
                try{
                    DB.insertPersonne(p);
                    Toast.makeText(v.getContext(),"Personne inserted",Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Personne can not be inserted "+e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
            p.setImage(imageBitmap);
        }
       // super.onActivityResult(requestCode, resultCode, data);
    }
}
