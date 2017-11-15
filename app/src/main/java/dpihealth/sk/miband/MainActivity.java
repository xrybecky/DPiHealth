package dpihealth.sk.miband;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import dpihealth.sk.miband.fragments.BluetoothConnectionFragment;
import dpihealth.sk.miband.handlers.MessageHandler;
import dpihealth.sk.miband.interfaces.RequestCodes;
import dpihealth.sk.miband.receivers.DeviceFound;


// zdroje
// https://developer.android.com/guide/topics/connectivity/bluetooth.html

public class MainActivity extends AppCompatActivity {

    private DeviceFound deviceFoundReceiver;

    private String LOG_TAG = "M_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void searchBluetoothDevices(View view){

        view.setVisibility(View.INVISIBLE);

        BluetoothConnectionFragment searchDeviceFragment = new BluetoothConnectionFragment();
        searchDeviceFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainActivityLayout, searchDeviceFragment)
                .commit();
    }
}
