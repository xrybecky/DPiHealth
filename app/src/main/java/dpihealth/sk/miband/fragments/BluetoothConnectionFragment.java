package dpihealth.sk.miband.fragments;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dpihealth.sk.miband.MainActivity;
import dpihealth.sk.miband.R;
import dpihealth.sk.miband.helpers.BluetoothDevicesListAdapter;
import dpihealth.sk.miband.interfaces.RequestCodes;
import dpihealth.sk.miband.receivers.DeviceFound;

/**
 * A simple {@link Fragment} subclass.
 */
public class BluetoothConnectionFragment extends Fragment {


    private Context context;
    private View view;
    private List<BluetoothDevice> devicesList;
    private RecyclerView devicesListView = null;
    private BroadcastReceiver mBroadcastReceiver;
    private LinearLayout btConnectionLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bluetooth_connection, container, false);

        btConnectionLayout = (LinearLayout)view.findViewById(R.id.btConnectionFragment);


        devicesList = new ArrayList<BluetoothDevice>();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        checkBTConnection();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            // bluetooth has been enabled
            case RequestCodes.ENABLE_BT_REQUEST:
                handleBTEnabled();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    public void checkBTConnection(){

        if(this.context != null){

            // get bluetooth adapter
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            // Device does not support bluetooth
            if(bluetoothAdapter == null){

                // TODO: make as snackbar
                Toast.makeText(context, getString(R.string.bt_not_found), Toast.LENGTH_SHORT).show();

            }else{

                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, RequestCodes.ENABLE_BT_REQUEST);
                }else{
                    handleBTEnabled();
                    bluetoothAdapter.startDiscovery();
                }
            }
        }
    }

    public void handleBTEnabled() {


        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(devicesListView == null){

                    // create RecyclerView
                    devicesListView = new RecyclerView(context);
                    devicesListView.setLayoutManager(new LinearLayoutManager(context));
                    devicesListView.setLayoutParams(new RecyclerView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT)
                    );

                    // atach adapter to RecyclerView
                    devicesListView.setAdapter(new BluetoothDevicesListAdapter(context, devicesList));

                    if(btConnectionLayout != null){
                        btConnectionLayout.removeAllViews();
                        btConnectionLayout.addView(devicesListView);
                    }
                }

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                devicesList.add(device);
                devicesListView.getAdapter().notifyItemChanged(devicesList.size() - 1);

            }
        };

        context.registerReceiver(mBroadcastReceiver, filter);
    }

}
