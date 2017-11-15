package dpihealth.sk.miband.helpers;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import dpihealth.sk.miband.interfaces.Communication;

/**
 * Created by rybec on 06.11.2017.
 */

public class BluetoothCommunicationCallback extends BluetoothGattCallback {

    private String LOG_TAG = "M_LOG";

    Context context;
    private Communication communicationInterface;

    public BluetoothCommunicationCallback(Context context){
        this.context = context;
        this.communicationInterface = (Communication)context;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);

        if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {

            communicationInterface.onDeviceConnected(gatt.getDevice());

            // discovering bluetooth services
            gatt.discoverServices();

        }else if(newState == BluetoothProfile.STATE_DISCONNECTED){

            // new connecting loop
            gatt.getDevice().connectGatt(context, false, this);

        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);

        Log.i(LOG_TAG, "onServicesDiscovered");

        if (status == BluetoothGatt.GATT_SUCCESS) {


            Log.i(LOG_TAG, "run Pair");

            BluetoothGattCharacteristic pair = gatt.getService(Communication.UUID_MILI_SERVICE).getCharacteristic(Communication.UUID_CHAR_pair);

            if (pair != null) {
                pair.setValue(new byte[]{2});
                gatt.writeCharacteristic(pair);
            }
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        Log.i(LOG_TAG, "onCharacteristicRead");

        byte[] b = characteristic.getValue();

        if (characteristic.getUuid().equals(Communication.UUID_CHAR_REALTIME_STEPS)){
            communicationInterface.onStepsUpdate((0xff & b[0] | (0xff & b[1]) << 8));
            gatt.readCharacteristic(characteristic);
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);

        Log.i(LOG_TAG, "onCharacteristicWrite");

        if(characteristic.getUuid().equals(Communication.UUID_CHAR_pair)){
            BluetoothGattCharacteristic realSteps = gatt.getService(Communication.UUID_MILI_SERVICE).getCharacteristic(Communication.UUID_CHAR_REALTIME_STEPS);
            gatt.readCharacteristic(realSteps);
        }
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        Log.i(LOG_TAG, "onReadRemoteRssi");
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);

        Log.i(LOG_TAG, "onCharacteristicChanged");
    }
}
