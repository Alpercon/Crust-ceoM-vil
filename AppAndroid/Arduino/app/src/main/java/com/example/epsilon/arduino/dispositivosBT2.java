package com.example.epsilon.arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class dispositivosBT2 extends AppCompatActivity {


    private static final String TAG = "dispositivosBT2";

    ListView lista;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothAdapter mAdapter;
    private ArrayAdapter<String> dispositivosListaAdapter;

    private int REQUEST_ENABLE_BT = 74;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bt2);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        VerificarEstadoBT();


        dispositivosListaAdapter = new ArrayAdapter<>(this, R.layout.nombre_dispositivos);
        lista = (ListView) findViewById(R.id.lista2);
        lista.setAdapter(dispositivosListaAdapter);
        lista.setOnItemClickListener(mDeviceClickListener);
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
        if (pairedDevices.size() > 0)
        {
            for (BluetoothDevice device : pairedDevices) {
                dispositivosListaAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    }


    // Configura la accion "OncLick para la lista
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {

            //Se obtiene la MAC del dispositivo
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            //toma un EXTRA_DEVICE_ADDRESS que es la dirección MAC.
            Intent next = new Intent(dispositivosBT2.this, EvitadorObstaculos.class);
            next.putExtra(EXTRA_DEVICE_ADDRESS, address);
            startActivity(next);
        }
    };




    private void VerificarEstadoBT() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if (!mAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
}
