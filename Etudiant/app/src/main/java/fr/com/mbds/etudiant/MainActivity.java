package fr.com.mbds.etudiant;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ListAdapter adapter = null;
    ArrayList<Personne> LP;
    ListView mylist;
    TextView txt ;
    TextView txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LP = new ArrayList<Personne>();
        //Personne p = new Personne("Toto","Titi");
        //LP.add(p);
        mylist=(ListView)findViewById(R.id.personnes);
        adapter=new MyAdapterHelper(this);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Personne p = (Personne)adapter.getItem(position);
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                i.putExtra("personne",p);
                startActivity(i);

            }
        });
    }
/*
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return LP.size();
    }

    @Override
    public Object getItem(int position) {
        return LP.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return LP.indexOf(LP.get(position));
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView;
        if(convertView==null){
         returnView=  View.inflate(this,R.layout.etudiant,null);
        }
        else{
             returnView=convertView;
        }
        txt=(TextView)returnView.findViewById(R.id.name);
        txt.setText(LP.get(position).getNom());
        txt2=(TextView)returnView.findViewById(R.id.surname);
        txt2.setText(LP.get(position).getPrenom());
        return returnView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        if(LP.size()==0)
            return true;
        else
            return false;
    } */
}
