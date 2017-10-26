package fr.com.mbds.etudiant;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by jperk on 25/10/2017.
 */

public class MyAdapterHelper implements ListAdapter {
    ArrayList<Personne> LP;
    Context ctx;
    TextView txt ;
    TextView txt2;
    ImageView myimg;
    public MyAdapterHelper(Context ctx) {
        LP = new ArrayList<Personne>();

        LP.add(new Personne("toto", "titi"));
        LP.add(new Personne("perkova", "jaglika"));
        this.ctx = ctx;
    }

    public MyAdapterHelper(ArrayList<Personne> LP, Context ctx) {
        super();
        this.LP = LP;
        this.ctx = ctx;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

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

            returnView=View.inflate(ctx, R.layout.etudiant, null);
        }
        else {
            returnView = convertView;
        }

        txt=(TextView)returnView.findViewById(R.id.name);
        txt.setText(LP.get(position).getNom());
        txt2=(TextView)returnView.findViewById(R.id.surname);
        txt2.setText(LP.get(position).getPrenom());
        myimg=(ImageView)returnView.findViewById(R.id.imageID);
        myimg.setImageBitmap(LP.get(position).getImage());
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
        if(LP.isEmpty())
            return true;
        else
            return false;
    }
}
