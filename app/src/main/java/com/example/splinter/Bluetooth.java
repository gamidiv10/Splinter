package com.example.splinter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

/**
 * Created by Jasper Jiao on 2019-10-28.
 */

public class Bluetooth extends AppCompatActivity{
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn;

    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);

        mStatusBlueTv = (TextView) findViewById(R.id.statusBluetoothTv);
        mPairedTv     = (TextView) findViewById(R.id.pairedTv);
        mBlueIv       = (ImageView) findViewById(R.id.bluetoothIv);
        mOnBtn        = (Button) findViewById(R.id.onBtn);
        mOffBtn       = (Button) findViewById(R.id.offBtn);
        mDiscoverBtn  = (Button) findViewById(R.id.discoverableBtn);
        mPairedBtn    = (Button) findViewById(R.id.pairedBtn);

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
}
