package fr.com.mbds.etudiant;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import utils.DbBitmapUtility;

public class MainActivity extends AppCompatActivity{
    private final static int REQUEST_ENABLE_BT = 1;
    ListAdapter adapter = null;
    ArrayList<Personne> LP;
    ListView mylist;
    TextView txt ;
    TextView txt2;
    Button add;
    Button bluetooth;
    //BluetoothAdapter mBluetoothAdapter ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            Toast.makeText(this,"Bluetooth visible",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=(Button)findViewById(R.id.buttonAdd);
        bluetooth=(Button)findViewById(R.id.buttonBluetooth);
        mylist=(ListView)findViewById(R.id.personnes);
        adapter=new MyAdapterHelper(this);
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Personne p = (Personne)adapter.getItem(position);
                Bundle args = new Bundle();
                args.putByteArray("img", DbBitmapUtility.getBytes(p.getImage()));
                args.putSerializable("personne",p);
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                i.putExtras(args);
                //i.putExtra("personne",p);
                startActivity(i);

            }
        });
       bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(),"The device does not support Bluetooth", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                /*else {
                    //Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                }*/

            }
        });
        this.add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AjoutPersonne.class);
                startActivity(intent);
            }
        });

}

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i=getIntent();
        finish();
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }
}
