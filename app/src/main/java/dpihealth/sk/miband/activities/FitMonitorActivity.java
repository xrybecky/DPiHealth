package dpihealth.sk.miband.activities;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.UUID;

import dpihealth.sk.miband.R;
import dpihealth.sk.miband.helpers.BluetoothCommunicationCallback;
import dpihealth.sk.miband.interfaces.Communication;

public class FitMonitorActivity extends AppCompatActivity implements Communication{

    private String LOG_TAG = "M_LOG";
    private TextView deviceConnectStateView;
    private TextView currentStepsView;

    LinearLayout connectingLayout = null;
    LinearLayout stepCounterLayout = null;
    private BluetoothGatt mainGatt;
    private BluetoothDevice mainDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_monitor);

        connectingLayout = (LinearLayout)findViewById(R.id.connectingLayout);
        stepCounterLayout = (LinearLayout)findViewById(R.id.stepCounterLayout);

        mainDevice = getIntent().getParcelableExtra(Communication.INTER_ACTIVITY_DEVICE_BUNDLE_KEY);

        connectToDevice();

        deviceConnectStateView = (TextView)findViewById(R.id.deviceConnectState);
        currentStepsView = (TextView)findViewById(R.id.currentSteps);
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.mainGatt.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        connectToDevice();
    }

    public void connectToDevice(){

        if(mainGatt != null){
            mainGatt.connect();
        }else{
            if(mainDevice != null) {
                mainGatt = mainDevice.connectGatt(this, false, new BluetoothCommunicationCallback(this));
            }
        }
    }

    public void tryConnect(UUID serviceUUID){



    }

    public void measureHeartRate(View view){

        BluetoothGattCharacteristic hearthRate;
        hearthRate = mainGatt.getService(Communication.UUID_SERVICE_HEART_RATE).getCharacteristic(Communication.UUID_CHAR_HEART_RATE_MEASUREMENT);

        if (hearthRate != null) {
            hearthRate.setValue(new byte[]{2});
            mainGatt.readCharacteristic(hearthRate);
            Log.i("M_LOG", "heartRate written");
        }else{
            Log.i("M_LOG", "char is null");
        }

    }

    @Override
    public void onStepsUpdate(final int steps) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentStepsView.setText("" + steps);
            }
        });

    }

    @Override
    public void onDeviceConnectingFailed(final BluetoothDevice device) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {deviceConnectStateView.setText( device.getName() + " connecting failed");
            }
        });

    }

    @Override
    public void onDeviceConnected(final BluetoothDevice device) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectingLayout.setVisibility(View.INVISIBLE);
                stepCounterLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onDeviceDisconnected(final BluetoothDevice device) {

        connectingLayout.setVisibility(View.VISIBLE);
        stepCounterLayout.setVisibility(View.INVISIBLE);
    }
}
