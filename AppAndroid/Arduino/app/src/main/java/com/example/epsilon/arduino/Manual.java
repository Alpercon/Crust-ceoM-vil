package com.example.epsilon.arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Manual extends AppCompatActivity {


    private int REQUEST_ENABLE_BT = 74;
    Button btnAvanzar, btnDetenerse, btnDesconectar, btnDerecha,btnIzquierda,btnRetroceder;
    TextView IdBufferIn;

    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static String address = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);


        btnAvanzar = (Button) findViewById(R.id.avanzar);
        btnDetenerse = (Button) findViewById(R.id.detenerse);
        btnDerecha = (Button)findViewById(R.id.derecha);
        btnIzquierda = (Button)findViewById(R.id.izquierda);
        btnRetroceder = (Button)findViewById(R.id.retroceder);

        btnDesconectar = (Button) findViewById(R.id.IdDesconectar);
        IdBufferIn = (TextView) findViewById(R.id.IdBufferIn);

        bluetoothIn = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
                        IdBufferIn.setText("Dato: " + dataInPrint);
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();

        //Onclick para buttons
        btnDetenerse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MyConexionBT.write("0");
            }
        });
        btnAvanzar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MyConexionBT.write("1");
            }
        });
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MyConexionBT.write("2");
            }
        });
        btnDerecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MyConexionBT.write("3");
            }
        });
        btnIzquierda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MyConexionBT.write("4");
            }
        });
        btnDesconectar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (btSocket!=null){
                    try {btSocket.close();}
                    catch (IOException e)
                    { Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();;}
                }
                finish();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        address = intent.getStringExtra(dispositivosBT.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try{
            btSocket = createBluetoothSocket(device);
        }catch(IOException e){
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        try{
            btSocket.connect();
        }catch (IOException e){
            try{
                btSocket.close();
            } catch (IOException e2){}
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
        MyConexionBT.write("x");
    }

    @Override
    public void onPause(){
        super.onPause();
        try{
            btSocket.close();
        } catch(IOException e2){}
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    private void VerificarEstadoBT() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }


    private class ConnectedThread extends Thread{
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;


            while (true) {
                try{
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);

                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String input)
        {
            try{
                mmOutStream.write(input.getBytes());
            }catch(IOException e){

                Toast.makeText(getBaseContext(), "Falló la conexión", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
