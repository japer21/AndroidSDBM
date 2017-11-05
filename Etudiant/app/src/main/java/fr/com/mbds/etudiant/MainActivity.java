package fr.com.mbds.etudiant;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private static final int DISCOVERABLE_BT_REQUEST_CODE = 2;
    private static final int DISCOVERABLE_DURATION = 300;
    private final static int REQUEST_ENABLE_BT = 1;
    ListAdapter adapter = null;
    ArrayList<Personne> LP;
    ListView mylist;
    TextView txt ;
    TextView txt2;
    Button add;
    Button bluetooth;
    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };
    BluetoothAdapter mBluetoothAdapter ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RESULT_OK:
                Toast.makeText(this,"Bluetooth visible",Toast.LENGTH_SHORT).show();
                //break;
            case DISCOVERABLE_DURATION:
                Toast.makeText(this,"Your device is now discoverable by other devices for \n" + DISCOVERABLE_DURATION + " seconds",Toast.LENGTH_LONG).show();
                //break;
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
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

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

       bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(),"The device does not support Bluetooth", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                else if(mBluetoothAdapter.isEnabled()){
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {
                        // There are paired devices. Get the name and address of each paired device.
                        for (BluetoothDevice device : pairedDevices) {
                            String deviceName = device.getName();
                            String deviceHardwareAddress = device.getAddress(); // MAC address
                        }
                    }
                    else{
                        discoverDevices();
                        makeDiscoverable();

                    }



                }
                /*else if(mBluetoothAdapter.isEnabled()){
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {
                        // There are paired devices. Get the name and address of each paired device.
                        for (BluetoothDevice device : pairedDevices) {
                            String deviceName = device.getName();
                            String deviceHardwareAddress = device.getAddress(); // MAC address
                        }
                    }
                    else{
                        Intent discoverableIntent =
                                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivity(discoverableIntent);

                    }



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

    protected void discoverDevices(){
        // To scan for remote Bluetooth devices
        if (mBluetoothAdapter.startDiscovery()) {
            Toast.makeText(getApplicationContext(), "Discovering other bluetooth devices...",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Discovery failed to start.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected void makeDiscoverable(){
        if(mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
            // Make local device discoverable
            Intent discoverableIntent = new
                    Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
            startActivityForResult(discoverableIntent, DISCOVERABLE_BT_REQUEST_CODE);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i=getIntent();
        finish();
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);

    }
    /*
    @Override
    protected void onResume() {
        super.onResume();

        // Register the BroadcastReceiver for ACTION_FOUND
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
       // mBluetoothAdapter.cancelDiscovery();
        //mBluetoothAdapter.disable();
    }
}
