package com.example.splinter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Jasper Jiao on 2019-10-09.
 */

public class Bluetooth extends AppCompatActivity{
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    SendReceive sendReceive;
    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn, msendBtn;
    TextView status;
    BluetoothAdapter mBlueAdapter;
    ImageView imageView;

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING=2;
    static final int STATE_CONNECTED=3;
    static final int STATE_CONNECTION_FAILED=4;
    static final int STATE_MESSAGE_RECEIVED=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);
        // linking view elements in bluetooth layout
        mStatusBlueTv = (TextView) findViewById(R.id.statusBluetoothTv);
        mPairedTv     = (TextView) findViewById(R.id.pairedTv);
        mBlueIv       = (ImageView) findViewById(R.id.bluetoothIv);
        mOnBtn        = (Button) findViewById(R.id.onBtn);
        mOffBtn       = (Button) findViewById(R.id.offBtn);
        mDiscoverBtn  = (Button) findViewById(R.id.discoverableBtn);
        mPairedBtn    = (Button) findViewById(R.id.pairedBtn);
        msendBtn =(Button) findViewById(R.id.sendBtn);
        // Checking device is Bluetooth compatible
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBlueAdapter == null){
            mStatusBlueTv.setText("Bluetooth is not available");
        }
        else {
            mStatusBlueTv.setText("Bluetooth is available");
        }

        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter == null){
                    mStatusBlueTv.setText("Bluetooth is not available");
                }
                else{
                    showToast("Turning On Bluetooth...");

                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                    showToast("Bluetooth is already on");
                }
            }
        });

        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter == null){
                    mStatusBlueTv.setText("Bluetooth is not available");
                }
                else{
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });
// Turning of bluetooth
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter == null){
                    mStatusBlueTv.setText("Bluetooth is not available");
                }
                else{
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth Off");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                    showToast("Bluetooth is already off");
                }
            }
        });

        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter == null){
                    mStatusBlueTv.setText("Bluetooth is not available");
                }
                else {
                    mPairedTv.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device: devices){
                        mPairedTv.append("\nDevice: " + device.getName()+ ", " + device);
                    }
                    showToast("Turn on bluetooth to get paired devices");
                }
            }
        });
        // Function to send data using bluetooth
        msendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.happyicon);
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,50,stream);
                byte[] imageBytes=stream.toByteArray();

                int subArraySize=400;

                sendReceive.write(String.valueOf(imageBytes.length).getBytes());

                for(int i=0;i<imageBytes.length;i+=subArraySize){
                    byte[] tempArray;
                    tempArray= Arrays.copyOfRange(imageBytes,i,Math.min(imageBytes.length,i+subArraySize));
                    sendReceive.write(tempArray);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                } else {
                    showToast("could't on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // Handling bluetooth connections
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what)
            {
                case STATE_LISTENING:
                    status.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    status.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    status.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    status.setText("Connection Failed");
                    break;
                case STATE_MESSAGE_RECEIVED:

                    byte[] readBuff= (byte[]) msg.obj;
                    Bitmap bitmap=BitmapFactory.decodeByteArray(readBuff,0,msg.arg1);

                    imageView.setImageBitmap(bitmap);

                    break;
            }
            return true;
        }
    });
    private class SendReceive extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive (BluetoothSocket socket)
        {
            bluetoothSocket=socket;
            InputStream tempIn=null;

            OutputStream tempOut=null;

            try {
                tempIn= bluetoothSocket.getInputStream();
                tempOut= bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream=  tempIn;
            outputStream= tempOut;
        }
        public void run()
        {
            byte[] buffer = null;
            int numberOfBytes = 0;
            int index=0;
            boolean flag = true;
            while(true)
            {
                if(flag)
                {
                    try {
                        byte[] temp = new byte[inputStream.available()];
                        if(inputStream.read(temp)>0)
                        {
                            numberOfBytes=Integer.parseInt(new String(temp,"UTF-8"));
                            buffer=new byte[numberOfBytes];
                            flag=false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        byte[] data=new byte[inputStream.available()];
                        int numbers=inputStream.read(data);

                        System.arraycopy(data,0,buffer,index,numbers);
                        index=index+numbers;
                        if(index == numberOfBytes)
                        {
                            handler.obtainMessage(STATE_MESSAGE_RECEIVED,numberOfBytes,-1,buffer).sendToTarget();
                            flag = true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
                outputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
